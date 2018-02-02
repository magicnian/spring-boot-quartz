package com.magicnian.quartz.springbootquartz.response;

import com.magicnian.quartz.springbootquartz.enums.ResponseCode;

import java.io.Serializable;

/**
 * Created by liunn on 2018/2/2.
 */
public class ResponseModel implements Serializable {

    private static final long serialVersionUID = -8341532575800845381L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示信息
     */
    private String msg;

    /**
     * 具体响应model
     */
    private Object data;

    public ResponseModel() {
        this.code = ResponseCode.SUCCESS.getCode();
        this.msg = ResponseCode.SUCCESS.getMsg();
    }

    public ResponseModel(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
