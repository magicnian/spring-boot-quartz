package com.magicnian.quartz.springbootquartz.util.http;


import com.magicnian.quartz.springbootquartz.config.HttpConfig;
import com.magicnian.quartz.springbootquartz.util.HttpCustomConnect;
import groovy.util.logging.Slf4j;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: liuniannian
 * @Date: 2018/11/3 14:42
 * @Description:
 */
@Slf4j
public class HttpCrawler {

    private Request request;

    private HttpClientConfig config;

    public static HttpCrawler custom() {
        return new HttpCrawler();
    }

    private HttpCrawler() {
        request = new Request();
    }

    public HttpCrawler config(HttpClientConfig config) {
        this.config = config;
        return this;
    }

    public HttpCrawler client(CloseableHttpClient client) {
        this.request.setClient(client);
        return this;
    }

    public HttpCrawler url(String url) {
        this.request.setUrl(url);
        return this;
    }

    public HttpCrawler data(Map<String, String> data) {
        this.request.setData(data);
        return this;
    }

    public HttpCrawler json(String jsonStr) {
        this.request.setJsonStr(jsonStr);
        return this;
    }

    public HttpCrawler headers(Map<String, String> headers) {
        this.request.setHeaders(headers);
        return this;
    }

    public HttpCrawler cookie(BasicCookieStore cookieStore) {
        this.request.setCookieStore(cookieStore);
        return this;
    }

    public HttpCrawler get() {
        this.request.setMethod(Method.GET);
        return this;
    }

    public HttpCrawler post() {
        this.request.setMethod(Method.POST);
        return this;
    }

    public HttpCrawler proxy(HttpHost proxy) {
        this.request.setProxy(proxy);
        return this;
    }

    public HttpCrawler charset(String charset) {
        this.request.setCharset(charset);
        return this;
    }

    public HttpCrawler timeout(int timeout) {
        this.request.setTimeout(timeout);
        return this;
    }

    public HttpCrawler autoRedirect(boolean autoRedirect) {
        this.request.setAutoRedirect(autoRedirect);
        return this;
    }


    public enum Method {
        GET, POST
    }


    @Data
    private static class Request {
        private CloseableHttpClient client;
        private String url;
        private Map<String, String> data;
        private String jsonStr;
        private Map<String, String> headers;
        private BasicCookieStore cookieStore;
        private Method method = Method.GET;
        private boolean isJson;
        private HttpHost proxy;
        private int timeout;
        private boolean autoRedirect = true;
        private String cookieSpecs = CookieSpecs.STANDARD;
        private String charset = "utf-8";
    }


    public HttpClientResponse excute() throws Exception {
        if (request.getMethod().equals(Method.GET)) {
            return get(request);
        } else if (request.getMethod().equals(Method.POST)) {
            return post(request);
        } else {
            return null;
        }
    }

    private HttpClientResponse get(Request req) throws Exception {
        return deal(req);
    }

    private HttpClientResponse post(Request req) throws Exception {
        return deal(req);
    }

    private HttpClientResponse deal(Request req) throws Exception {
        HttpRequestBase hrb = null;
        HttpClientResponse httpClientResponse = new HttpClientResponse();
        try {
            CloseableHttpClient client = (null == req.getClient() ? HttpClients.createDefault() : req.getClient());

            String url = req.getUrl();
            boolean isPost = req.getMethod().equals(Method.POST);
            hrb = isPost ? new HttpPost(url) : new HttpGet(url);

            Map<String, String> headerMaps = req.getHeaders();

            if (null != headerMaps && !headerMaps.isEmpty()) {
                Set<Map.Entry<String, String>> set = headerMaps.entrySet();
                for (Map.Entry<String, String> e : set) {
                    hrb.addHeader(e.getKey(), e.getValue());
                }
            }

            if (isPost) {
                if (req.isJson()) {
                    StringEntity stringEntity = new StringEntity(req.getJsonStr(), req.getCharset());
                    stringEntity.setContentType("application/json");
                    ((HttpPost) hrb).setEntity(stringEntity);
                } else if (null != req.getData() && !req.getData().isEmpty()) {
                    Map<String, String> param = req.getData();
                    List<NameValuePair> paramList = new ArrayList<>();
                    for (Map.Entry<String, String> e : param.entrySet()) {
                        paramList.add(new BasicNameValuePair(e.getKey(), e.getValue()));
                    }
                    UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(paramList, req.getCharset());
                    ((HttpPost) hrb).setEntity(paramEntity);
                }
            }

            hrb.setConfig(generateRequestConfig(req, config));


            HttpEntity httpEntity = null;
            try {
                CloseableHttpResponse response = client.execute(hrb);
                httpEntity = response.getEntity();
                httpClientResponse.setResponseBytes(EntityUtils.toByteArray(httpEntity));
                httpClientResponse.setBodyStr(getResposneString(httpClientResponse.getResponseBytes(), req));
                httpClientResponse.setStatusCode(response.getStatusLine().getStatusCode());
                httpClientResponse.setBasicCookieStore(req.getCookieStore());
            } finally {
                EntityUtils.consume(httpEntity);
            }
        } finally {
            hrb.releaseConnection();
        }
        return httpClientResponse;
    }

    private String getResposneString(byte[] resposneBytes, Request req) throws Exception {
        return new String(resposneBytes, req.getCharset());
    }


    /**
     * 生成每个请求的requestconfig
     * 优先取Request对象中的值，
     * 如果Request对象中没赋值，
     * 再取HttpConfig中的值
     *
     * @param request
     * @param config
     * @return
     */
    private RequestConfig generateRequestConfig(Request request, HttpClientConfig config) {
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        int timeout = request.getTimeout() == 0 ? config.getTimeout() : request.getTimeout();
        requestConfigBuilder.setSocketTimeout(timeout).setConnectTimeout(timeout).setConnectionRequestTimeout(timeout);

        boolean autoRedirect = request.isAutoRedirect() ? request.isAutoRedirect() : config.isAutoRedirect();
        requestConfigBuilder.setRedirectsEnabled(autoRedirect);

        String cookieSpec = request.getCookieSpecs() != null ? request.getCookieSpecs() : config.getCookieSpec();
        requestConfigBuilder.setCookieSpec(cookieSpec);

        HttpHost proxy = request.getProxy() != null ? request.getProxy() : config.getProxy();
        requestConfigBuilder.setProxy(proxy);

        return requestConfigBuilder.build();
    }
}
