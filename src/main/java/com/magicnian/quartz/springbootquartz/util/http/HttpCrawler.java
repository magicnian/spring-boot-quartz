package com.magicnian.quartz.springbootquartz.util.http;


import groovy.util.logging.Slf4j;
import lombok.Data;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.Map;

/**
 * @Auther: liuniannian
 * @Date: 2018/11/3 14:42
 * @Description:
 */
@Slf4j
public class HttpCrawler {

    private Request request;

    public HttpCrawler custom() {
        return new HttpCrawler();
    }

    private HttpCrawler() {
        request = new Request();
    }


    public HttpCrawler client(CloseableHttpClient client) {
        this.request.setClient(client);
        return this;
    }

    public HttpCrawler url(String url) {
        this.request.setUrl(url);
        return this;
    }

    public HttpCrawler data(Map<String,String> data){
        this.request.setData(data);
        return this;
    }

    public HttpCrawler headers(Map<String,String> headers){
        this.request.setHeaders(headers);
        return this;
    }

    public HttpCrawler cookie(BasicCookieStore cookieStore){
        this.request.setCookieStore(cookieStore);
        return this;
    }

    public HttpCrawler get(){
        this.request.setMethod(Method.GET);
        return this;
    }

    public HttpCrawler post(){
        this.request.setMethod(Method.POST);
        return this;
    }

    public HttpCrawler proxy(HttpHost proxy){
        this.request.setProxy(proxy);
        return this;
    }



    public enum Method {
        GET, POST
    }


    @Data
    private static class Request {
        private CloseableHttpClient client;
        private String url;
        private Map<String,String> data;
        private Map<String,String> headers;
        private BasicCookieStore cookieStore;
        private Method method = Method.GET;
        private HttpHost proxy;
    }
}
