package com.yixin.alixjob.service.impl;

import com.jcraft.jsch.SftpException;
import com.xxl.job.core.log.XxlJobLogger;
import com.yixin.alixjob.common.Constants;
import com.yixin.alixjob.dto.attachment.AttachmentLoadDetail;
import com.yixin.alixjob.mapper.master.AttachmentExportMapper;
import com.yixin.alixjob.service.AttachmentExportService;
import com.yixin.alixjob.utils.javaShellUtils.JavaShellUtil;
import com.yixin.alixjob.utils.sftp.SFTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * @Author: lishuang
 * @Date: 2018/11/19 9:13
 */

@Slf4j
@Service
public class AttachmentExportServiceImpl implements AttachmentExportService {

    private static String ASQBHTXT = "asqbh.txt";
    private static String FILETYPETXT = "fileType.txt";
    private static String CPMCLOUDFILE_SH = "cpMCloudFile.sh";

    private static String COPY_OLDFILE_SH = "copyOldFile.sh";
    private static String MKDIR_SH = "mkdir.sh";

    private static String TAR_SH = "tar.sh";

    @Resource
    AttachmentExportMapper attachmentExportMapper;


    @Value("${attachment.export.new.path}")
    private String newPath;   //新附件导出路径

    @Value("${attachment.export.old.path}")
    private String oldPath;  //老附件导出路径

    @Value("${attachment.export.script}")
    private String scriptPath;  //脚本路径(tar.sh)

    @Value("${attachment.export.script.new}")
    private String newScriptPath; //新附件处理脚本路径

    @Value("${attachment.export.script.old}")
    private String oldScriptPath;//老附件处理脚本路径

    @Value("${attachment.export.tar.path}")
    private String tarExportPath;//*.tar 路径

    @Value("${ftp.port}")
    private int ftpPort;
    @Value("${ftp.host}")
    private String ftpHost;
    @Value("${ftp.username}")
    private String ftpUsername;
    @Value("${ftp.password}")
    private String ftpPassword;
    @Value("${ftp.data.path}")
    private String ftpDataPath;


    /**
     * 导出附件
     */
    @Override
    public void exportFile() throws FileNotFoundException, SftpException {

        //流水号
        String tradeNo = attachmentExportMapper.selectOneTradeNo();
        if (StringUtils.isEmpty(tradeNo)) {
            XxlJobLogger.log("无需要处理的任务");
            return;
        }
        XxlJobLogger.log("任务开始");
        //处理任务
        Map<String, String> map = handleTask(tradeNo);
        String tarPath = map.get("tarPath");
        String tarName = map.get("tarName");

        //更新状态
        attachmentExportMapper.updateAttachmentExportStatus(tradeNo, Constants.SUCCESS_STATUS, tarPath, tarName);
        XxlJobLogger.log("任务结束");
    }

    /**
     * 创建处理订单附件的脚本
     * @param detailList
     * @param newfileFlag
     * @throws FileNotFoundException
     */
    private void createScript(List<AttachmentLoadDetail> detailList, String newfileFlag) throws FileNotFoundException {

        //新附件,生成处理新附件的脚本
        /*
        * asqbh.txt  :每行一条申请编号
        * fileType.txt :每行一个附件小类 已经附件小类(文件的重命名名称) 如：mortgageRegistrationCertificate 抵押登记证
        * */
        if (Constants.NEWFILE_FLAG_NEW.equals(newfileFlag)) {
            HashMap<Object, Object> fileType = new HashMap<>();
            HashSet<String> asqbhs = new HashSet<>();
            File asqbhtxt = new File(newScriptPath + File.separator + ASQBHTXT);
            File fileTypeTxt = new File(newScriptPath + File.separator + FILETYPETXT);
            PrintWriter asqbhWriter = new PrintWriter(new FileOutputStream(asqbhtxt));
            PrintWriter fileTypeWriter = new PrintWriter(new FileOutputStream(fileTypeTxt));


            for (AttachmentLoadDetail detail : detailList) {
                fileType.put(detail.getFileClassSubType(), detail.getFileClassSubTypeName());
                asqbhs.add(detail.getAsqbh());
            }

            for (String asqbh : asqbhs) {
                asqbhWriter.println(asqbh);
            }

            Set<Map.Entry<Object, Object>> entries = fileType.entrySet();
            for (Map.Entry<Object, Object> entry : entries) {
                fileTypeWriter.println(entry.getKey() + " " + entry.getValue());
            }
            asqbhWriter.flush();
            fileTypeWriter.flush();
            IOUtils.closeQuietly(asqbhWriter);
            IOUtils.closeQuietly(fileTypeWriter);


        } else {

            //老附件，生成处理老附件的脚本
            /*
            * mkdir.sh  ：以申请编号建立文件夹
            * copyOldFile.sh ：复制文件
            *
            * */
            File copyOldFileSH = new File(oldScriptPath + File.separator + COPY_OLDFILE_SH);
            File mkdirSH = new File(oldScriptPath + File.separator + MKDIR_SH);
            PrintWriter copyFileSHWriter = new PrintWriter(new FileOutputStream(copyOldFileSH));
            PrintWriter mkdirSHWriter = new PrintWriter(new FileOutputStream(mkdirSH));
            HashSet<String> asqbhs = new HashSet<>();
            HashMap<String, Integer> asqbhAndFjdlAndFjxl = new HashMap<>(detailList.size());
            for (AttachmentLoadDetail detail : detailList) {
                int sameClazz=0;
                asqbhs.add(detail.getAsqbh());
                String key = detail.getAsqbh()+"_"+detail.getFileClassType()+"_"+detail.getFileClassSubType();
                if(asqbhAndFjdlAndFjxl.containsKey(key)){
                    Integer count = asqbhAndFjdlAndFjxl.get(key);
                    count++;
                    sameClazz=count.intValue();
                    asqbhAndFjdlAndFjxl.put(key,count);
                }else{
                    sameClazz=0;
                    asqbhAndFjdlAndFjxl.put(key,Integer.valueOf(0));
                }
                String filePath = detail.getFilePath();
                int suffixLength = filePath.lastIndexOf(".");
                String suffix = filePath.substring(suffixLength + 1);

                if(sameClazz==0){
                    copyFileSHWriter.println("cp " + detail.getFilePath() + " "
                            + oldPath + File.separator + detail.getAsqbh() + File.separator
                            + detail.getFileClassSubTypeName()+ "." + suffix + ";");
                }else{
                    copyFileSHWriter.println("cp " + detail.getFilePath() + " "
                            + oldPath + File.separator + detail.getAsqbh() + File.separator
                            + detail.getFileClassSubTypeName()+"_"+asqbhAndFjdlAndFjxl.get(key).intValue() + "." + suffix + ";");
                }
            }
            mkdirSHWriter.println("rm -rf "+oldPath+File.separator+"*");
            for (String asqbh : asqbhs) {
                mkdirSHWriter.println("mkdir -p " + oldPath + File.separator + asqbh + ";");
            }
            copyFileSHWriter.flush();
            mkdirSHWriter.flush();
            IOUtils.closeQuietly(copyFileSHWriter);
            IOUtils.closeQuietly(mkdirSHWriter);
        }

    }


    /**
     * 创建打tar包的脚本
     * @param tarName
     * @throws FileNotFoundException
     */
    private void createTarScript(String tarName) throws FileNotFoundException {

        File tar = new File(scriptPath + File.separator + TAR_SH);
        if (tar.exists()) {
            tar.delete();
        }

        PrintWriter tarSHWriter = new PrintWriter(new FileOutputStream(tar));
        tarSHWriter.println("cd " + tarExportPath + ";");
        tarSHWriter.println("rm  -f *.tar;");
        tarSHWriter.println("tar -cvf " + tarName + " * ;");
        tarSHWriter.println("rm -rf " + newPath + File.separator + "*;");
        tarSHWriter.println("rm -rf " + oldPath + File.separator + "*;");

        tarSHWriter.flush();
        IOUtils.closeQuietly(tarSHWriter);

    }

    private Map<String, String> handleTask(String tradeNo) throws FileNotFoundException, SftpException {

        List<AttachmentLoadDetail> newDetailList = attachmentExportMapper.selectExportNewAttachmentDetails(tradeNo);

        List<AttachmentLoadDetail> oldDetailList = attachmentExportMapper.selectExportOldAttachmentDetails(tradeNo);

        //复制新的附件
        if (!CollectionUtils.isEmpty(newDetailList)) {
            createScript(newDetailList, Constants.NEWFILE_FLAG_NEW);
            //创建文件夹,复制文件
            int out = JavaShellUtil.ExecCommand(newScriptPath + File.separator + CPMCLOUDFILE_SH);
            log.info("新附件复制文件sh:{}处理结果:{}", newScriptPath + File.separator + CPMCLOUDFILE_SH, out);
        }


        //复制老的附件(先创建目标目录,再复制文件)
        if (!CollectionUtils.isEmpty(oldDetailList)) {
            createScript(oldDetailList, Constants.NEWFILE_FLAG_OLD);
            //创建文件夹
            int out = JavaShellUtil.ExecCommand(oldScriptPath + File.separator + MKDIR_SH);
            log.info("老附件创建文件夹sh:{}处理结果:{}", oldScriptPath + File.separator + MKDIR_SH, out);
            //复制文件
            int copyOut = JavaShellUtil.ExecCommand(oldScriptPath + File.separator + COPY_OLDFILE_SH);
            log.info("老附件创建文件夹sh:{}处理结果:{}", oldScriptPath + File.separator + COPY_OLDFILE_SH, copyOut);
        }


        String tarName = tradeNo + ".tar";
        //执行tar 打包脚本
        createTarScript(tarName);
        int command = JavaShellUtil.ExecCommand(scriptPath + File.separator + TAR_SH);
        log.info("附件导出tarSH:{}处理结果:{}", scriptPath + File.separator + TAR_SH, command);

        //上传到ftp
        SFTPUtil sftpUtil = new SFTPUtil(ftpUsername, ftpPassword, ftpHost, ftpPort);
        sftpUtil.login();
        sftpUtil.upload(ftpDataPath, tarName, new FileInputStream(new File(tarExportPath + File.separator + tarName)));
        sftpUtil.logout();


        Map<String, String> map = new HashMap<>(4);
        map.put("tarName", tarName);
        map.put("tarPath", ftpDataPath + File.separator + tarName);

        return map;

    }

}
