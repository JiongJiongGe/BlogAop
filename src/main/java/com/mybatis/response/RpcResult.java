package com.mybatis.response;

import java.io.Serializable;

/**
 * Created by yunkai on 2017/12/29.
 */
public class RpcResult<T> implements Serializable{

    private Boolean success;

    private Integer errorCode;

    private String errorMsg;

    private T value;

    private RpcResult(){

    }

    public static <T> RpcResult<T> ofSuccess(T value){
        RpcResult rpcResult = new RpcResult();
        rpcResult.setSuccess(true);
        rpcResult.setValue(value);
        return rpcResult;
    }

    public static <T> RpcResult<T> ofError(Integer errorCode, String errorMsg){
        RpcResult rpcResult = new RpcResult();
        rpcResult.setValue(false);
        rpcResult.setErrorCode(errorCode);
        rpcResult.setErrorMsg(errorMsg);
        rpcResult.setValue(null);
        return  rpcResult;
    }



    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RpcResult{" +
                "success=" + success +
                ", errorCode=" + errorCode +
                ", errorMsg=" + errorMsg +
                ", value=" + value +
                '}';
    }
}
