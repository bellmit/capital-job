package com.yixin.alixjob.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yixin.alixjob.mapper.master.GenerateFileMapper;
import com.yixin.alixjob.service.GenerateFileService;
import com.yixin.alixjob.utils.javaShellUtils.JavaShellUtil;
import com.yixin.alixjob.utils.sftp.SFTPUtil;
import com.yixin.alixjob.utils.time.DateUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
@Service
@Slf4j
public class GenerateFileServiceImpl implements GenerateFileService{
	public static final MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");

	@Resource
	private GenerateFileMapper generateFileMapper;

	private String dataFormat = "yyyyMMdd";

	private String busiPrifixStr = "LEASE_ALIX_";

	private String repayPrifixStr = "REPAY_ALIX_";

	private String txtFileType = ".txt";

	private String fileSuffix = ".tar.gz";

	@Value("${busiMaxNum}")
	private int busiMaxNum;

	@Value("${repayMaxNum}")
	private int repayMaxNum;

	@Value("${busiWritePath}")
	private String busiWritePath;

	@Value("${repayWritePath}")
	private String repayWritePath;

	@Value("${busiShellScriptPath}")
	private String busiShellScriptPath;

	@Value("${repayShellScriptPath}")
	private String repayShellScriptPath;



	@Value("${mergeBusiTxtPath}")
	private String mergeBusiTxtPath;

	@Value("${mergeRepayTxtPath}")
	private String mergeRepayTxtPath;

	@Value("${compressFilePath}")
	private String compressFilePath;



	@Value("${ifrsSftpAddress}")
	private String ifrsSftpAddress;

	@Value("${ifrsSftpPort}")
	private int ifrsSftpPort;

	@Value("${ifrsSftpUsername}")
	private String ifrsSftpUsername;

	@Value("${ifrsSftpPassword}")
	private String ifrsSftpPassword;

	@Value("${ifrsSftpFilePath}")
	private String ifrsSftpFilePath;


	@Value("${noticeBusiUrl}")
	private String noticeBusiUrl;

	@Value("${noticeRepayUrl}")
	private String noticeRepayUrl;



	/**
	 * 生成业务数据文件
	 * @throws Exception
	 */
	@Override
	public void generateBusinessFile() throws Exception {
		//1.判断是否为每月1号中午12点之前，如果是12点以前，删除临时表，重新读取数据插入临时表，12点之后(重推)直接读取临时表，防止数据变化
		//2.判断数据总数量，分割数据条目为10万，确定分页数量
		//3.确定多少页为一个文件，文件流写文件，在服务器上写好之后，shell命令文件合并后打包，sftp送到目标服务器路径下
		//4.http请求通知对方文件已经合成

		//获取当前月份第一天0点的日期
		Date beginTime = DateUtil.beginOfMonth(new Date());
		//获取当前月份第一天12点的日期
		Date endTime = getNineTimeOfTheMonth();
		//当前日期
		Date date = new Date();

		int busiCount = 0;
		//当前日期处于当月1号0点到9点之间，重新抓取数据进临时表
		if(date.before(endTime)&&date.after(beginTime)){
			//清空历史数据
			generateFileMapper.deleteBusiData();
			log.info(">>>deleteBusi删除历史业务数据完成!!");
			//插入临时表
			generateFileMapper.insertBusiDataToTemp();
			log.info(">>>insertBusi插入业务数据完成!!");
			//写入文件
			busiCount = generateFileMapper.getBusinessDataTotalCount();
			if(busiCount>0){
				log.info(">>>writeBusiData业务数据文件写入开始!!");
				writeBusiText(busiCount);
				log.info(">>>writeBusiData业务数据文件写入完成!!");
			}

		}else{
			//其他时间直接读取临时表
			//写入文件
			busiCount = generateFileMapper.getBusinessDataTotalCount();
			if(busiCount>0){
				log.info(">>>writeBusiData业务数据文件写入开始!!");
				writeBusiText(busiCount);
				log.info(">>>writeBusiData业务数据文件写入完成!!");
			}
		}

		log.info(">>>busiCount数据总量:"+busiCount);

		int retCode = -1;
		if(StringUtils.isNotBlank(busiShellScriptPath)&&busiCount>0){
			//拼接命令   脚本路径+" "+合并文件名称+" "+压缩文件名称
			String cmd =busiShellScriptPath+" "+busiPrifixStr+getDateStr()+txtFileType+" "+busiPrifixStr+getDateStr()+fileSuffix;

			log.info(">>>mergeBusiPlanData执行合并开始!!");
			retCode=JavaShellUtil.ExecCommand(cmd);
			log.info(">>>mergeBusiPlanData执行合并结束!!,返回值："+retCode);
		}

		if(retCode==0){
			//执行shell脚本成功
			String textFileName = busiPrifixStr + getDateStr() + txtFileType;

			log.info("sendNoticeBusiData发送通知开始！");
			sendNotice(noticeBusiUrl, ifrsSftpFilePath, textFileName, busiCount);
			log.info("sendNoticeBusiData发送通知结束！");
		}else{
			log.error("mergeBusiPlanData压缩文件不成功，执行命令返回值为："+retCode);
			throw new Exception("mergeBusiPlanData执行脚本压缩失败！");
		}

	}



	/**
	 * 生成还款计划数据文件
	 * @throws Exception
	 */
	@Override
	public void generateRepaymentFile() throws Exception {
		//1.判断是否为每月1号中午12点之前，如果是12点以前，删除临时表，重新读取数据插入临时表，12点之后(重推)直接读取临时表，防止数据变化
		//2.判断数据总数量，分割数据条目为10万，确定分页数量
		//3.确定多少页为一个文件，文件流写文件，在服务器上写好之后，shell命令文件合并后打包，sftp送到目标服务器路径下
		//4.http请求通知对方文件已经合成

		//获取当前月份第一天0点的日期
		Date beginTime = DateUtil.beginOfMonth(new Date());
		//获取当前月份第一天12点的日期
		Date endTime = getNineTimeOfTheMonth();
		//当前日期
		Date date = new Date();

		int repayCount = 0;
		//当前日期处于当月1号0点到9点之间，重新抓取数据进临时表
		if(date.before(endTime)&&date.after(beginTime)){
			//清空历史数据
			generateFileMapper.deleteRepayData();
			log.info(">>>deleteRepay删除还款计划数据完成!!");
			//插入临时表
			generateFileMapper.insertRepayDataToTemp();
			log.info(">>>insertRepay插入还款计划数据完成!!");
			//写入文件

			repayCount = generateFileMapper.getRepayDataTotalCount();
			if(repayCount>0){
				log.info(">>>writeRepayPlanData还款计划文件写入开始!!");
				writeRepayText(repayCount);
				log.info(">>>writeRepayPlanData还款计划文件写入完成!!");
			}
		}else{
			//其他时间直接读取临时表
			//写入文件
			repayCount = generateFileMapper.getRepayDataTotalCount();
			if(repayCount>0){
				log.info(">>>writeRepayPlanData还款计划文件写入开始!!");
				writeRepayText(repayCount);
				log.info(">>>writeRepayPlanData还款计划文件写入完成!!");
			}
		}

		log.info(">>>repayCount数据总量:"+repayCount);
		int retCode = -1;
		if(StringUtils.isNotBlank(repayShellScriptPath)&&repayCount>0){
			//拼接命令   脚本路径+" "+合并文件名称+" "+压缩文件名称
			String cmd =repayShellScriptPath+" "+repayPrifixStr+getDateStr()+txtFileType+" "+repayPrifixStr+getDateStr()+fileSuffix;

			log.info(">>>mergeRepayPlanData执行合并开始!!");
			retCode = JavaShellUtil.ExecCommand(cmd);
			log.info(">>>mergeRepayPlanData执行合并结束!!,返回值："+retCode);
		}

		if(retCode==0){
			//执行shell脚本成功
			String textFileName = repayPrifixStr + getDateStr() + txtFileType;

			log.info("sendNoticeRepayPlanData发送通知开始！");
			sendNotice(noticeRepayUrl, ifrsSftpFilePath, textFileName, repayCount);
			log.info("sendNoticeRepayPlanData发送通知结束！");

		}else{
			log.error("mergeRepayPlanData压缩文件不成功，执行命令返回值为："+retCode);
			throw new Exception("mergeRepayPlanData执行脚本压缩失败！");
		}


	}


	/**
	 * 写入业务数据文件
	 * @throws IOException
	 */
	private void writeBusiText(int busiCount) throws IOException{
		//统计临时表总数量
		log.info(">>>业务数据总量："+busiCount);

		//统计页数
		int pageCount = busiCount/busiMaxNum;
		if(busiCount%busiMaxNum>0){
			//最后再加一页
			pageCount = pageCount + 1;
		}
		log.info(">>>业务数据分页总页数："+pageCount);

		String filePath = busiWritePath;
		File pa = new File(filePath);
		if(!pa.exists()){
			pa.mkdirs();
		}

		//清空该目录下的历史文件
		deleteFile(pa);

		for(int i = 1;i<=pageCount;i++){
			log.info(">>>业务数据第"+i+"页开始写入！");
			long starttime = System.currentTimeMillis();

			PageHelper.startPage(i,busiMaxNum);
			List<String> busiStr = generateFileMapper.selectBusiDataByIndex();
			PageInfo<String> pageInfo = new PageInfo<>(busiStr);

			List<String> reList = pageInfo.getList();

			if(CollectionUtils.isNotEmpty(reList)){
				String fileName = filePath+File.separator+"busiData"+i+txtFileType;//文件路径

				//文件不可追加，否则重复推送会加大文件
				FileOutputStream fos = new FileOutputStream(fileName);
				OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
				BufferedWriter bw = new BufferedWriter(osw);

				for(int j=0;j<reList.size();j++){
					/*if(j==reList.size()-1&&i==pageCount){
						bw.write(reList.get(j));// 往已有的文件上添加字符串
					}else{*/
					bw.write(reList.get(j)+"\r\n");// 往已有的文件上添加字符串
//					}
				}

				IOUtils.closeQuietly(bw);
				IOUtils.closeQuietly(osw);
				IOUtils.closeQuietly(fos);

				long endtime = System.currentTimeMillis();
				log.info(">>>业务数据第"+i+"页写入完成！  耗时："+(endtime-starttime)/1000+"秒！！！！");
			}
		}
	}


	/**
	 * 写入还款计划文件
	 * @throws IOException
	 */
	private void writeRepayText(int count) throws IOException{
		//统计临时表总数量
		log.info(">>>repayPlanData还款计划数据总量："+count);

		//统计页数
		int pageCount = count/repayMaxNum;
		if(count%repayMaxNum>0){
			//最后再加一页
			pageCount = pageCount + 1;
		}
		log.info(">>>repayPlanData还款计划数据分页总页数："+pageCount);

		String filePath = repayWritePath;
		File pa = new File(filePath);
		if(!pa.exists()){
			pa.mkdirs();
		}

		//清空该目录下的历史文件
		deleteFile(pa);

		for(int i = 1;i<=pageCount;i++){
			log.info(">>>repayPlanData还款计划数据第"+i+"页开始写入！");
			long starttime = System.currentTimeMillis();

			PageHelper.startPage(i,repayMaxNum);
			List<String> busiStr = generateFileMapper.selectRepayDataByIndex();
			PageInfo<String> pageInfo = new PageInfo<>(busiStr);

			List<String> reList = pageInfo.getList();

			if(CollectionUtils.isNotEmpty(reList)){
				String fileName = filePath+File.separator+"repayData"+i+txtFileType;//文件路径

				//文件不可追加，否则重复推送会加大文件
				FileOutputStream fos = new FileOutputStream(fileName);
				OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
				BufferedWriter bw = new BufferedWriter(osw);
				for(int j=0;j<reList.size();j++){
					/*if(j==reList.size()-1&&i==pageCount){
						bw.write(reList.get(j));// 往已有的文件上添加字符串
					}else{*/
					bw.write(reList.get(j)+"\r\n");// 往已有的文件上添加字符串
//					}
				}

				IOUtils.closeQuietly(bw);
				IOUtils.closeQuietly(osw);
				IOUtils.closeQuietly(fos);


				long endtime = System.currentTimeMillis();
				log.info(">>>repayPlanData还款计划数据第"+i+"页写入完成！  耗时："+(endtime-starttime)/1000+"秒！！！！");
			}
		}
	}



	/**
	 * 发送文件到目标ftp
	 * @throws Exception
	 */
	private void sendFileToSftp(String sftpFileName) throws Exception {
		// 服务器压缩文件路径
		String localCompressFilePath = compressFilePath + sftpFileName;

		File file = new File(localCompressFilePath);
		if (!file.exists()) {
			throw new Exception("sendSftp"+sftpFileName+"压缩文件不存在！");
		}

		SFTPUtil util = new SFTPUtil(ifrsSftpUsername, ifrsSftpPassword, ifrsSftpAddress, ifrsSftpPort);
		util.login();
		FileInputStream st = new FileInputStream(file);
		util.upload(ifrsSftpFilePath, sftpFileName, st);
		util.logout();

	}


	private void sendNotice(String noticeUrl,String filePath,String fileName,int recordNum) throws ClientProtocolException, URISyntaxException, IOException{

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Map<String,Object> param= new HashMap<>();
		param.put("transFlag", "start-end");
		param.put("sysIdentification", "ALIX");
		param.put("transTime", sdf.format(new Date()));
		param.put("recordNum", recordNum);
		param.put("dataPath", filePath);
		param.put("dataFileName", fileName);

		String json = JSON.toJSONString(param);
		log.info("sendNotice请求参数："+json);

		RequestBody body = RequestBody.create(JSONTYPE, json);
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(noticeUrl)
				.post(body)
				.build();
		Call call = client.newCall(request);

		Response response = call.execute();
		String result;
		if(response.isSuccessful()){
			result = response.body().string();
			log.info("sendNotice"+fileName+"发送通知返回："+result);
			JSONObject jsonObject = JSON.parseObject(result);
			String retCode = jsonObject.getString("resCode");
			log.info("sendNotice"+fileName+"发送通知返回结果为："+retCode);
		}else{
			log.info("sendNotice"+fileName+"发送通知失败");
			throw new IOException("Unexpected code " + response);
		}
	}


	/**
	 * 获取当前日期字符串
	 * @return
	 */
	private String getDateStr(){
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH,0);
		return new SimpleDateFormat(dataFormat).format(cale.getTime());
	}



	/**
	 * 获取当月1号12点的日期
	 * @return
	 */
	private Date getNineTimeOfTheMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 9); //早上9点
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}


	private void deleteFile(File file){
		if (file.isFile()){//判断是否为文件，是，则删除
			log.info(file.getAbsolutePath());//打印路径
			file.delete();
		}else{
			String[] childFilePath = file.list();//获取文件夹下所有文件相对路径
			for (String path:childFilePath){
				File childFile= new File(file.getAbsolutePath()+"/"+path);
				deleteFile(childFile);//递归，对每个都进行判断
			}
		}
	}



	public static void main(String[] args) throws IOException, URISyntaxException {
		/*HttpClientUtil http = new HttpClientUtil();
		String fileName = "LEASE_ALIX_20181031.txt";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Map<String,Object> param= new HashMap<>();
		param.put("transFlag", "start-end");
		param.put("sysIdentification", "ALIX");
		param.put("transTime", sdf.format(new Date()));
		param.put("recordNum", 10000);
		param.put("dataPath", "");
		param.put("dataFileName", "LEASE_ALIX_20181031.txt");

		HttpResult result = http.doPost("http://192.168.177.69:8080/i9-web/alixBusiness/receiveData", param);
		if(null!=result&&200==result.getCode()){
			String retStr = result.getData();
			log.info("sendNotice"+fileName+"发送通知返回："+retStr);
			JSONObject jsonObject = JSON.parseObject(retStr);
			String retCode = jsonObject.getString("resCode");
			log.info("sendNotice"+fileName+"发送通知返回结果为："+retCode);
		}else{
			log.info("sendNotice"+fileName+"发送通知失败");
		}*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		System.out.println(sdf.format(new Date()));

	}
}
