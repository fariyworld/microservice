package com.mace.microservice.common.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

/**
 * description: Web 统一异常类
 * <br />
 * Created by mace on 15:29 2018/7/16.
 */
@JSONType(orders = {
        "url",
        "ex_message",
        "exception",
        "stackTrace",
})
public class ExceptionInfo<T extends Exception> {

    private String url;
    private String ex_message;
//    @JSONField(serialize = false)
    private T exception;//Exception
    private String stackTrace;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEx_message() {
        return ex_message;
    }

    public void setEx_message(String ex_message) {
        this.ex_message = ex_message;
    }

    public T getException() {
        return exception;
    }

    public void setException(T exception) {
        this.exception = exception;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }
}
