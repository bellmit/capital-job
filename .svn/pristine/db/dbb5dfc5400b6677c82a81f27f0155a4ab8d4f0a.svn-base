package com.yixin.alixjob.service.mortageswitch;

public interface MortageSwitchService {

    /**
     * 切换抵押模式（影像件影响） 先抵后放 还是先放后抵
     * 合同审批通过之后35天内影像件未审核通过，切换经销商为请款时需上传抵押登记证
     * 对应 tbase_dealer 的asfxbzhfk  aescsfxbz
     */
    public void swtichModelForImages();

    /**
     * 切换抵押模式（归档影响） 先抵后放 还是先放后抵
     * 影像件审批通过之后30天内未进行归档，切换经销商为请款时需上传抵押登记证
     * 对应 tbase_dealer 的asfxbzhfkgd  aescsfxbzgd
     */
    public void swtichModelForArchive();

}
