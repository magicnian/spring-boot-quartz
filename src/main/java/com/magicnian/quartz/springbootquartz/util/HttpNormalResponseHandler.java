package com.magicnian.quartz.springbootquartz.util;

import org.apache.http.HttpEntity;

/**
 * Created by liunn on 2018/1/31.
 */
public class HttpNormalResponseHandler implements HttpCustomResponseHandler<HttpCustomConnect.Response>{
    @Override
    public HttpCustomConnect.Response handle(HttpEntity httpEntity) {
        return null;
    }
}
