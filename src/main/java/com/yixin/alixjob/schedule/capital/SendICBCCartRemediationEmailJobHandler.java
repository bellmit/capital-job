package com.yixin.alixjob.schedule.capital;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;
import com.yixin.alixjob.common.CapitalConstants;
import com.yixin.alixjob.service.capitalService.SendICBCEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 工行退车补息邮件提醒
 * @author xlp
 */
@JobHandler(value = "sendICBCCartRemediationEmailJobHandler")
@Component
public class SendICBCCartRemediationEmailJobHandler extends IJobHandler {

    @Autowired
    private SendICBCEmailService sendICBCEmailService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        XxlJobLogger.log(">>>>>>>>>>>sendICBCCartRemediationEmailJobHandler>>>>>>>>>>>>");
        sendICBCEmailService.sendICBCEmail(CapitalConstants.TYPE_1);

        return new ReturnT<String>(200, "ok===0000");
    }

}
