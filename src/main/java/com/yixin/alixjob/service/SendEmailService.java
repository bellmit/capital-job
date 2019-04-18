package com.yixin.alixjob.service;

import com.yixin.alixjob.dto.email.AlixSendEmail;
import com.yixin.alixjob.mapper.master.SendEmailMapper;
import com.yixin.core.biz.BaseService;

public interface SendEmailService extends BaseService<SendEmailMapper,AlixSendEmail>{

    /**
     * 根据类型发送Email
     * @param type
     */
    void  sendEail(String type);

    /**
     * 根据Emai类型获取模板内容
     * @param EmailType
     * @return
     */
    String getContextByEmailType(String EmailType,String context)  throws Exception ;
}
