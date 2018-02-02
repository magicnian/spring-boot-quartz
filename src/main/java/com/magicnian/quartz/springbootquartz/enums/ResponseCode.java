package com.magicnian.quartz.springbootquartz.enums;

/**
 * 响应码枚举
 * Created by liunn on 2018/2/2.
 */
public enum ResponseCode {

    SUCCESS(200, "成功"),
    FAIL(500, "失败"),
    ILLEGAL_ARGS(400, "请求参数错误");

    private int code;

    private String msg;

    private ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
