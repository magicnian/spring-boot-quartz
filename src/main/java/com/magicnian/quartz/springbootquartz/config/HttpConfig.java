package com.magicnian.quartz.springbootquartz.config;

import lombok.Data;

/**
 * Created by liunn on 2018/1/31.
 */
@Data
public class HttpConfig {

    private String configId;

    private Integer retryTimes;

    private Integer timeoutMs = 30000;

    private Integer useGzip = 1;

    private String userAgent = "Mozilla/5.0 (Windows NT 10.0, Win64, x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36";

    private String succesCodes = "200";

    private String charset = "utf-8";

    private Integer useProxy;

    private boolean autoRedirect = false;

    private boolean isUseLaxRedirectStrategy = false;

    public boolean useProxy() {
        return this.getUseProxy() == 1;
    }

    public boolean useGzip() {
        return this.getUseGzip() == 1;
    }

    public HttpConfig(String configId) {
        this.configId = configId;
    }

}
