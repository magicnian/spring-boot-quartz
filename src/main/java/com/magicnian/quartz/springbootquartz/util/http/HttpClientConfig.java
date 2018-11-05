package com.magicnian.quartz.springbootquartz.util.http;

import lombok.Data;
import org.apache.http.HttpHost;
import org.apache.http.client.config.CookieSpecs;

/**
 * @Auther: liuniannian
 * @Date: 2018/11/3 15:12
 * @Description:
 */
@Data
public class HttpClientConfig {

    //超时时间
    private int timeout;

    //重试次数
    private int retryTimes;

    //默认的user-agent
    private String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";

    //代理
    private HttpHost proxy;

    //编码
    private String charset = "utf-8";

    //cookie策略
    private String cookieSpec = CookieSpecs.STANDARD;

    //是否自动跳转
    private boolean autoRedirect = false;

    //是否所有请求都自动跳转
    private boolean isUseLaxRedirectStrategy = false;
}
