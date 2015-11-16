package com.maoxin.model;

/**
 * Created by matthewyao on 2015/9/17.
 */
public class ResultResponse<T> {
    private boolean isok = false;
    private String message = "";
    private String callBackURL = "";
    private T object;

    public boolean isIsok() {
        return isok;
    }

    public void setIsok(boolean isok) {
        this.isok = isok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCallBackURL() {
        return callBackURL;
    }

    public void setCallBackURL(String callBackURL) {
        this.callBackURL = callBackURL;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
