package com.yixin.alixjob.service.mortageswitch.factory;

import com.yixin.alixjob.service.mortageswitch.MortageSwitchService;
import com.yixin.alixjob.utils.base.ApplicationContextUtils;


public class MortageSwitchFactory {

    public static MortageSwitchService getMortageSwitchService(String type){
        String name = MortageSwitchEnum.getName(type);
        return  (MortageSwitchService) ApplicationContextUtils.getBean(name);
    }


}
