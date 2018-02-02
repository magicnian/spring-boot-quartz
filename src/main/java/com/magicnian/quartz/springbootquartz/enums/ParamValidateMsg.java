package com.magicnian.quartz.springbootquartz.enums;

/**
 * 参数验证结果
 * Created by liunn on 2018/2/2.
 */
public enum ParamValidateMsg {

    IS_REQUIRED("不能为空"),
    IS_NUMBER("必须为数字"),
    IS_UNSIGNINT("必须为大于0的整数"),
    FORMAT_ERROR("格式不正确"),
    LENGTH_ERROR("长度不正确"),
    DATE_FORMAT("格式不正确");

    private String msg;

    private ParamValidateMsg(String msg)
    {
        this.msg = msg;
    }

    public String getMsg()
    {
        return msg;
    }
}
