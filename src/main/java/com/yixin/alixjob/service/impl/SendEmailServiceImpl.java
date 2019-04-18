package com.yixin.alixjob.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yixin.alixjob.common.Constants;
import com.yixin.alixjob.dto.email.AlixSendEmail;
import com.yixin.alixjob.mapper.master.SendEmailMapper;
import com.yixin.alixjob.service.SendEmailService;
import com.yixin.alixjob.utils.email.EmailUtil;
import com.yixin.core.biz.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import java.util.List;
@Service
@Slf4j
public class SendEmailServiceImpl extends BaseServiceImpl<SendEmailMapper,AlixSendEmail> implements SendEmailService{
    @Resource
    private SendEmailMapper sendEmailMapper;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private TemplateEngine templateEngine;
    @Override
    public void sendEail(String type) {
        //查询待发送的信息
        List<AlixSendEmail> lists =  sendEmailMapper.getAlixSendEmail(Constants.UN_SEND_EMAIL,Constants.MAX_NUM,type);
        //循环发送
        if(lists!=null && lists.size()>0){
           lists.forEach((sendEmailDto) -> this.sendEmailBody(sendEmailDto,type));
        }
    }

    private void sendEmailBody(AlixSendEmail sendEmailDto,String type){

        String sendStatus = "";
        try {
            //组装发送需要的信息
            if(StringUtils.isEmpty(sendEmailDto.getToMails())){
                log.error("接收者不能为空");
                sendStatus =Constants.SEND_EMAIL_FAIL;
            }
            //接收者和抄送者需要转换为list
            String[] toMails= sendEmailDto.getToMails().split(",");
            String[] ccMails=null;
            if(StringUtils.isNotEmpty(sendEmailDto.getCcMails())){
                ccMails=sendEmailDto.getCcMails().split(",");
            }
            //获得模板的内容
            String context = this.getContextByEmailType(type,sendEmailDto.getContextRemark());
            emailUtil.sendEmail(toMails,ccMails,sendEmailDto.getTitle(),
                    context,sendEmailDto.getFileAddress(),
                    sendEmailDto.getFileName(),true);
            sendStatus =Constants.SEND_EMAIL_SUCCESS;
        }catch (Exception e){
            log.error("发送邮件错误",e);
            sendStatus =Constants.SEND_EMAIL_FAIL;
        }
        //修改为已发送状态
        sendEmailMapper.updateSendEmailStatusById(sendEmailDto.getId(),sendStatus);
    }

    /**
     * 获得黑洞任务的邮件模板
     * @param json
     * @return
     * @throws Exception
     */
    private  String backholdContext(String json) throws Exception {

        if(StringUtils.isEmpty(json)){
            log.error("未找到模板使用的参数");
            throw new Exception("未找到模板使用的参数");
        }
        JSONObject jsonObject = (JSONObject) JSON.parse(json);
        String asqbh = jsonObject.getString("asqbh");
        //创建邮件正文
        Context con = new Context();
        con.setVariable("asqbh", asqbh);
        String emailContent = templateEngine.process("blackHoldEmailTemplate", con);
        return emailContent;
    }

    /**
     * 获得大于30W任务的邮件模板
     * @param json
     * @return
     * @throws Exception
     */
    private  String approveAmoutContext(String json) throws Exception {
        if(StringUtils.isEmpty(json)){
            log.error("未找到模板使用的参数");
            throw new Exception("未找到模板使用的参数");
        }
        JSONObject jsonObject = (JSONObject) JSON.parse(json);
        if(jsonObject ==null){
            log.error("未找到模板使用的参数");
            throw new Exception("未找到模板使用的参数");
        }
        String assignUser = jsonObject.getString("assignUser");
        String asqbh = jsonObject.getString("asqbh");
        if(StringUtils.isEmpty(assignUser) || StringUtils.isEmpty(asqbh) ){
            log.error("必输参数为空。无法发送邮件");
            throw new Exception("必输参数为空。无法发送邮件");
        }
        //创建邮件正文
        Context con = new Context();
        con.setVariable("brName", assignUser);
        con.setVariable("asqbh", asqbh);
        String emailContent = templateEngine.process("checkAmountEmailTemplate", con);
        return emailContent;
    }
    @Override
    public String getContextByEmailType(String EmailType,String context) throws Exception {
        if(Constants.TYPE_1.equals(EmailType)){
            return this.backholdContext(context);
        }else {
            return this.approveAmoutContext(context);
        }
    }
}
