
package com.yixin.core.exception.base;


import com.yixin.core.exception.BaseException;

/**
 * 系统基础异常类
 *
 * @author ace
 * @version 2018/1/13.
 */
public class SystemException extends BaseException {
    public SystemException(String message) {
        super(message);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException() {
        super();
    }
}
