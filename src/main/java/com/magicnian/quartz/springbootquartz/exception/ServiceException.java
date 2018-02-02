package com.magicnian.quartz.springbootquartz.exception;

/**
 * Created by liunn on 2018/2/2.
 */
public class ServiceException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 675054400927446700L;

    protected int code;

    protected String msg;

    public ServiceException()
    {

    }

    public ServiceException(String msg)
    {
        super(msg);
    }

    public ServiceException(Exception e)
    {
        super(e);
    }

    public ServiceException(String msg,Exception e)
    {
        super(msg,e);
    }

    public ServiceException(int code,String msg)
    {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }
}
