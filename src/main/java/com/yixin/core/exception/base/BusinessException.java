package com.yixin.core.exception.base;


import com.yixin.core.constants.RestCodeConstants;
import com.yixin.core.exception.BaseException;

/**
 * 业务异常基础类
 *
 * @author ace
 * @version 2018/1/13.
 */
public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message, RestCodeConstants.EX_BUSINESS_BASE_CODE);
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String message, int status) {
        super(message, status);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
