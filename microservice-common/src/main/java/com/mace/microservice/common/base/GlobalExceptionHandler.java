package com.mace.microservice.common.base;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * description: 全局异常处理 返回json
 * <br />
 * Created by mace on 11:58 2018/5/8.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * description: 返回 json
     * <br /><br />
     * create by mace on 2018/5/11 11:10.
     * @param ex
     * @param request
     * @param handlerMethod
     * @return: com.bonc.common.ResponseMessage<com.bonc.common.ExceptionInfo<java.lang.Exception>>
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestPackResponse<ExceptionInfo<Exception>> errorHandler(Exception ex, HttpServletRequest request, HandlerMethod handlerMethod){

        ExceptionInfo<Exception> exceptionInfo = new ExceptionInfo<Exception>();

        exceptionInfo.setException(ex);
        exceptionInfo.setStackTrace(ex.getStackTrace()[0].toString());
        exceptionInfo.setUrl(WebLogAspect.getRequestUrl(request));
        exceptionInfo.setEx_message(ex.getMessage());

        String requestId = (String) request.getAttribute("requestId");
        long START_TIME = (long) request.getAttribute("START_TIME");
        long END_TIME = (long) request.getAttribute("END_TIME");

        RestPackResponse<ExceptionInfo<Exception>> response = RestPackResponse.createByException(ex, exceptionInfo);

        response.setRequestId(requestId);
        response.setServerTime(new Date(START_TIME));
        response.setSpendTime((END_TIME - START_TIME));

        return response;
    }

}
