package com.yixin.alixjob.utils.email;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class EmailUtil {
    @Value("${spring.mail.username}")
    private String sendMail;
    @Autowired
    JavaMailSender javaMailSender;
    /**
     * 发送邮件
     * @param toMails 发送人
     * @param ccMails 抄送人
     * @param subject 标题
     * @param context 内容
     * @param fileAddress 附件地址
     * @param fileName 附件名称（需有附件后缀）
     * @param  userHtml 是否使用html格式
     */
    public void sendEmail(String[] toMails, String[] ccMails, String subject, String context, String fileAddress, String fileName,boolean userHtml) throws Exception {
        if (EmailUtil.isNullByArr(toMails)) {
            log.error("接收者为空");
            throw  new Exception("接收者为空");
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //发送者
        helper.setFrom(sendMail);
        //接收者
        helper.setTo(toMails);
        //发送的标题
        helper.setSubject(subject);
        //发送的内容
        helper.setText(context,userHtml);
        //抄送者
        if (!EmailUtil.isNullByArr(ccMails)) {
            helper.setCc(ccMails);
        }
        //附件
        if (StringUtils.isNotEmpty(fileAddress)) {
            File file = new File(fileAddress);
            helper.addAttachment(fileName, file);
        }
        helper.setSentDate(new Date());
        javaMailSender.send(mimeMessage);

    }

    public  static  boolean isNullByArr(String [] arr){
        boolean f = false;
        if(arr==null ||(arr!=null && arr.length==0)){
            f=true;
        }
        return f;
    }
}
