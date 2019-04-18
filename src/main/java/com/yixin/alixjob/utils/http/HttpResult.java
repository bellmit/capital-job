package com.yixin.alixjob.utils.http;

/**
 * @description: http返回结果
 * @autor: calvin_chen
 * @createDate: 2017/12/8 15:46
 * @version: 1.0.0
 */
public class HttpResult {

    private Integer code;

    private String data;
    
    private String requestJson;
    
    private String url;
    

	public HttpResult() {

    }

    public HttpResult(Integer code, String data) {
        this.code = code;
        this.data = data;
    }
    
	public HttpResult(Integer code, String data, String requestJson, String url) {
    	this.code = code;
        this.data = data;
        this.requestJson = requestJson;
        this.url = url;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

	public String getRequestJson() {
		return requestJson;
	}

	public void setRequestJson(String requestJson) {
		this.requestJson = requestJson;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    
}