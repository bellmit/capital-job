package com.yixin.alixjob.service.mortageswitch.impl;

import com.yixin.alixjob.mapper.master.mortageswitch.MortageSwitchOldCarMapper;
import com.yixin.alixjob.service.mortageswitch.MortageSwitchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Service("oldCarMortageSwitch")
public class MortageSwitchByOldCarServiceImpl implements MortageSwitchService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private MortageSwitchOldCarMapper mapper;

    @Override
    public void swtichModelForImages() {

    }

    @Override
    public void swtichModelForArchive() {
        logger.info(">>>>>>>>>>>>切换抵押模式：归档二手车>>>>>>>>>>>>>start>>>>");
        //step1: 删除临时表数据
        mapper.deleteTemp();
        //step2: 获取需要切换的经销商（包括新老关系映射），并插入临时表
        mapper.saveTempForArchive();
        //step3: 切换临时表内经销商 aescsfxbzgd=1
        mapper.updateDealerForArchive();
        //step4: 插入同步oa系统数据表(切换)
        mapper.savePolicyChangeForArchive();
        //step5: 插入日志记录表(切换)
        mapper.saveChangeLogsForArchive();
        //step6: 插入切换提醒表
        mapper.saveRemidForArchive();
        //step7: 恢复新车先抵后放为否 asfxbzhfkgd=0
        mapper.recoverDealerForArchive();
        //step8: 插入同步oa系统数据表(恢复)
        mapper.savePolicyRecoverForArchive();
        //step9: 插入日志记录表(恢复)
        mapper.saveRecoverLogsForArchive();
        //step10: 更新切换提醒表
        mapper.updateRemidForArchive();
        //step11: 插入切换表
        mapper.saveDealerSwitchForArchive();
        //step12: 删除切换表
        mapper.deleteDealerSwitchForArchive();
        //step13: 删除临时表
        mapper.deleteTemp();
        logger.info("<<<<<<<<<<<<切换抵押模式：归档二手车<<<<<<<<<<<<<end<<<<");
    }
}
