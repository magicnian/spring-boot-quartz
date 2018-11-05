package com.magicnian.quartz.springbootquartz.util.http;

import lombok.Data;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * @Auther: liuniannian
 * @Date: 2018/11/5 10:48
 * @Description:
 */
@Data
public class HttpClientResponse {

    private String bodyStr;

    private byte[] responseBytes;

    private int statusCode;

    private BasicCookieStore basicCookieStore;

}
