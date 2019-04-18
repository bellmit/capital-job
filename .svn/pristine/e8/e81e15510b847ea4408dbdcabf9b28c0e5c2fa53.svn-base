package com.yixin.alixjob.service.capitalServiceImpI;

import com.yixin.alixjob.common.CapitalConstants;
import com.yixin.alixjob.dto.capital.ICBCEmailInfoDto;
import com.yixin.alixjob.mapper.master.capital.SendICBCEmailMapper;
import com.yixin.alixjob.service.capitalService.SendICBCEmailService;
import com.yixin.alixjob.utils.email.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: xlp
 * @Date: 2019/3/14
 */

@Slf4j
@Service
public class SendICBCEmailServiceImpl implements SendICBCEmailService {

    @Resource
    private SendICBCEmailMapper capitalMapper;

    @Autowired
    private EmailUtil emailUtil;
    /**
     * 根据type类型发送邮件
     */
    @Override
    public void sendICBCEmail(String type) throws Exception{

        if(CapitalConstants.TYPE_1.equals(type)){//工行退车补息邮件发送
            String sendAddress = "";

            //查询待发送的信息
            List<ICBCEmailInfoDto> lists =  capitalMapper.queryICBCEmailInfoList();
            //查询收件人邮箱
            List<String> addressList = capitalMapper.queryICBCEmailAddress(CapitalConstants.ICBC_EMAIL_ADDRESS);

            if (null != addressList && addressList.size()>0){
                sendAddress = addressList.get(0);
            }

           for (ICBCEmailInfoDto dto :lists){
               sendEmailBody(dto,type,sendAddress);
           }
        }

    }

    public void sendEmailBody(ICBCEmailInfoDto dto,String type,String address){
        String sendStatus = "";//发送状态
        try {
            String[] toMails =  {address}; //收信人
            String[] ccMails = null; //抄送人
            String title = "工行订单"+dto.getAsqbh()+"退车"; //邮件标题
            String context = "编号为"+dto.getAsqbh()+"的工行订单，退车成功，存在补息额"+dto.getFillRatesAmount()+"元"; //邮件内容
            String fileAddress = null; //附件地址
            String fileName = null; //附件内容
            emailUtil.sendEmail(toMails,ccMails,title,
                    context,fileAddress,
                    fileName,true);
            sendStatus = CapitalConstants.SEND_EMAIL_SUCCESS;
        }catch (Exception e){
            sendStatus =CapitalConstants.SEND_EMAIL_FAIL;
        }

        //更新发送状态
        capitalMapper.updateSendEmailStatusByAsqbh(dto.getAsqbh(),sendStatus);
    }
}
