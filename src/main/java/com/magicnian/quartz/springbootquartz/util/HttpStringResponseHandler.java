package com.magicnian.quartz.springbootquartz.util;

import org.apache.http.HttpEntity;

/**
 * Created by liunn on 2018/1/31.
 */
public class HttpStringResponseHandler implements HttpCustomResponseHandler<String>{

    @Override
    public String handle(HttpEntity httpEntity) {
        return null;
    }
}
