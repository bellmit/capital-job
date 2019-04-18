package com.yixin.alixjob.schedule;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@JobHandler(value = "testXXlJobHandler")
@Component
@Slf4j
public class testXXlJobHandler extends IJobHandler {
    public  ReturnT<String> execute(String var1) throws Exception{
       log.info("[Test] test = {}",var1);
       return  new ReturnT<String>();
    }
}
