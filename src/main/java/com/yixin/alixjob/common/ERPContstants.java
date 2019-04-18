package com.yixin.alixjob.common;

public class ERPContstants {
    /**
     * 来源系统
     */
    public static final String SOURCESYSTEM = "LHD";
    /**
     * 来源系统alix
     */
    public static final String SOURCESYSTEM_ALIX = "ALIX_SYSTEM";
    //合同激活接口编码
    public static final String INTERFACECODE_CONTRACTEFFECT = "LHD-02";
    //还款计划接口编码
    public static final String INTERFACECODE_REPAYPLAN = "LHD-03";
    //银行放款接口编码
    public static final String INTERFACECODE_LOANDFROMBANK = "LHD-04";
    //云信银行放款接口编码
    public static final String INTERFACECODE_YXLOANDFROMBANK = "ALIX_BANK_PAY_INTERFACE";
    
    //易鑫放款接口编码
    public static final String INTERFACECODE_LOANFROMYIXIN = "LHD-21";
    //未生效合同接口编码
    public static final String INTERFACECODE_CONTRACTNOTEFFECT = "LHD-12";
    //转自营合同接口编码
    public static final String INTERFACECODE_CONTRACTTOOWN = "LHD-18";
    //合同生效后取消接口编码
    public static final String INTERFACECODE_CONTRACTCANCEL = "LHD-19";
    //转自营后取消接口编码
    public static final String INTERFACECODE_TRANSFERCANCEL = "LHD-22";
    //包商担保费接口编码
    public static final String INTERFACECODE_BSGUARANTEEFEE = "LHD-01";
    //新网车抵贷划扣接口编码
    public static final String INTERFACECODE_XWPAYMENT = "LHD-24";
    //申请状态
    public static final String INTERFACECODE_ALIX_CONTRACTSTATUS = "ALIX_CONTRACTSTATUS_INTERFACE";
    //申请状态全量推送条数
    public static final String ERP_CONTRACTSTATUS_COUNT = "8000";
    //还款计划条数限制
    public static final String ROWLIMIT_REPAYPLAN = "200";
    //常规条数限制
    public static final String ROWLIMIT_COMMON = "1000";

}
