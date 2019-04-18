package com.yixin.alixjob.service.mortageswitch.impl;

import com.yixin.alixjob.mapper.master.mortageswitch.MortageSwitchResourceCarMapper;
import com.yixin.alixjob.service.mortageswitch.MortageSwitchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("resourceCarMortageSwitch")
public class MortageSwitchByResourceCarServiceImpl implements MortageSwitchService{


    private MortageSwitchResourceCarMapper mapper;

    @Override
    public void swtichModelForImages() {

    }

    @Override
    public void swtichModelForArchive() {

    }
}
