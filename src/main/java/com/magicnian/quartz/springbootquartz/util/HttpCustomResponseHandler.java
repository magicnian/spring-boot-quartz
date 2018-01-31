package com.magicnian.quartz.springbootquartz.util;

import org.apache.http.HttpEntity;

/**
 * Created by liunn on 2018/1/31.
 */
public interface HttpCustomResponseHandler<T> {

    T handle(HttpEntity httpEntity);
}
