package com.yixin.core.handler;


import com.yixin.core.common.InvokeResult;
import com.yixin.core.constants.InvokeResultEnum;
import com.yixin.core.exception.BaseException;
import com.yixin.core.exception.ExceptionUtil;
import com.yixin.core.exception.base.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常拦截处理器
 *
 */
@ControllerAdvice("com.yixin")
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public Object otherExceptionHandler(HttpServletResponse response, Exception ex) {
        log.error(ex.getMessage(), ex);
        InvokeResult<Object> invokeResult = new InvokeResult<>();
        invokeResult.failure(InvokeResultEnum.FAILURE.getReasonPhrase());
        return invokeResult;
    }

    @ExceptionHandler(BaseException.class)
    public Object baseExceptionHandler(HttpServletResponse response, BaseException ex) {

        log.error(ex.getMessage(), ex);
        InvokeResult<Object> invokeResult = new InvokeResult<>();
        invokeResult.failure(InvokeResultEnum.FAILURE.getReasonPhrase());
        return invokeResult;
    }




    /** 请求格式异常
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object ReadableException(HttpServletResponse response, HttpMessageNotReadableException ex) {
        log.error(ex.getMessage(), ex);
        return InvokeResult.fail(InvokeResultEnum.REQUESTVALID.getReasonPhrase(),InvokeResultEnum.REQUESTVALID.value());
    }

    /**
     *  BusinessException 业务异常处理
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Object businessExceptionHandler(HttpServletResponse response, BusinessException ex) {
        log.info(ex.getMessage(), ex);
        InvokeResult<Object> invokeResult = new InvokeResult<>();
        invokeResult.failure(ex.getMessage());
        return invokeResult;
    }

    /**
     * validation 统一异常处理
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public InvokeResult validationExceptionHandler(HttpServletResponse response, Exception ex) {
        String resultMessage = ExceptionUtil.getExceptionMessage(ex);
        log.info(resultMessage,ex.getMessage(), ex);
        return InvokeResult.fail(resultMessage, InvokeResultEnum.VALIDED.value());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Object handleThrowable(Throwable e) {
        log.error("{}", e);
        InvokeResult<Object> invokeResult = new InvokeResult<>();
        if (e instanceof Throwable) {
            invokeResult.failure(e.getMessage());
        } else {
            invokeResult.failure("系统异常");
        }
        return invokeResult;
    }
}
