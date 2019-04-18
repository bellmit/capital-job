package com.yixin.core.constants;


/**
 *
 * 公共状态返回码
 *
 */
public enum InvokeResultEnum{

    //返回成功
    OK("0000", "OK"),
    
    //返回标识
    SAMEREQUEST("0001", "  请求次数与最大请求数量相等 "),
    
    //返回失败
    FAILURE("9999", " System Exception "),

    VALIDED("9900","数据验证规则异常"),

    //返回失败
    REQUESTVALID("9904", "请求格式错误"),


	NOTSAMECOUNT("9901", " 请求订单数量不一致 "),
	
	NOCACHE("9902", " 非法请求 "),
	
	NOGETCACHE("9905", " 请勿频繁查询或者操作，请稍后重试 "),
	
	WRONGAPPID("9903", " 错误的应用编号 ");

    private final String value;

    private final String reasonPhrase;


    InvokeResultEnum(String value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public String value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

}
