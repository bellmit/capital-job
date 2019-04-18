package com.yixin.core.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yixin.core.constants.InvokeResultEnum;
import lombok.Data;


/**
 * 统一返回值
 * <p>
 * Package : com.yixin.yxcode.dto
 *
 * @author YixinCapital -- wujt
 * 2016年7月12日 上午10:50:10
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvokeResult<T extends Object> {
    private Object data;
    private String respMsg;
    private String respCode;

    public InvokeResult() {
        super();
    }
    /**
     * 构造一个失败的响应
     *
     * @param msg 失败描述
     * @return 失败的响应
     */
    public static InvokeResult fail(String msg) {
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.failure(msg);
        return invokeResult;
    }

    /**
     *  构造一个失败的响应
     * @param msg
     * @param respCode
     * @return
     */
    public static InvokeResult fail(String msg,String respCode) {
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setRespMsg(msg);
        invokeResult.setRespCode(respCode);
        return invokeResult;
    }

    /**
     * 构造一个成功的响应
     *
     * @param data 数据
     * @return 成功的响应
     */
    public static InvokeResult success(Object data) {
        InvokeResult invokeResult = new InvokeResult();
        invokeResult.success();
        invokeResult.setData(data);
        return invokeResult;
    }
    /**
     * success
     *
     * @return
     */
    public InvokeResult<T> success() {
        this.respCode = InvokeResultEnum.OK.value();
        this.respMsg = InvokeResultEnum.OK.getReasonPhrase();
        return this;
    }

    /**
     * failure
     * @param msg
     * @return
     */
    public InvokeResult<T> failure(String msg) {
        this.respCode = InvokeResultEnum.FAILURE.value();
        this.respMsg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
}
