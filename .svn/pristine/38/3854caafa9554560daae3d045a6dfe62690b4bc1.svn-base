package com.yixin.core.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public class ExceptionUtil {
    public static String getExceptionMessage(Exception ex){
        String resultMessage = "";
        BindingResult bindingResult = null;
        if (ex instanceof BindException) {
            bindingResult =  ((BindException) ex).getBindingResult();
        }else if (ex instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
        }else{
            resultMessage = ex.getMessage();
        }
        resultMessage += getExceptionMessage(bindingResult);
        if (resultMessage != null && resultMessage.length() > 0) {
            resultMessage = resultMessage.substring(0,resultMessage.length()-1);
        }
        return resultMessage;
    }

    public static String getExceptionMessage(BindingResult bindingResult){
        String resultMessage = "";
        if (bindingResult !=null) {
            List<FieldError> fieldErrorList =  bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrorList) {
                String field = fieldError.getField();
                String code = fieldError.getDefaultMessage();
                if (field != null && field.length() > 0 && code != null && code.length() > 0) {
                    String message = String.format("%s:%s", field, code);
                    resultMessage += message + ",";
                }
            }
        }
        return resultMessage;
    }
}
