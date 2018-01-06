package com.mybatis.response;

/**
 * Created by yunkai on 2017/12/29.
 */
public enum ErrorCode {

    BIZ_ERROR("%s错误", 100001);

    private String msg;

    private Integer code;

    ErrorCode(String msg, Integer code){
        this.msg = msg;
        this.code = code;
    }

    public String getMsg(Object... param) {
        return String.format(msg, param);
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
