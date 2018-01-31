package com.magicnian.quartz.springbootquartz.util;

import com.magicnian.quartz.springbootquartz.exception.HttpException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 参考Jsoup源码写的自定义http连接
 * Created by liunn on 2018/1/31.
 */
@Slf4j
public class HttpCustomConnect {

    private Request req;


    public enum Method {
        GET, POST
    }

    public static HttpCustomConnect custom() {
        return new HttpCustomConnect();
    }

    private HttpCustomConnect() {
        req = new Request();
    }


    public HttpCustomConnect httpClient(CloseableHttpClient client) {
        req.httpClient(client);
        return this;
    }

    public HttpCustomConnect connect(String url) {
        req.url(url);
        return this;
    }

    public HttpCustomConnect data(String name, String value) {
        req.data(name, value);
        return this;
    }

    public HttpCustomConnect data(Map<String, String> data) {
        if (null == data) {
            return this;
        }
        for (Map.Entry<String, String> entity : data.entrySet()) {
            req.data(entity.getKey(), entity.getValue());
        }
        return this;
    }

    public HttpCustomConnect get() {
        req.method(Method.GET);
        return this;
    }

    public HttpCustomConnect post() {
        req.method(Method.POST);
        return this;
    }

    public Method getMethod() {
        return req.method;
    }

    public HttpCustomConnect headers(Map<String, String> headers) {
        if (null == headers) {
            return this;
        }
        for (Map.Entry<String, String> entity : headers.entrySet()) {
            req.header(entity.getKey(), entity.getValue());
        }
        return this;
    }

    public HttpCustomConnect header(String name, String value) {
        req.header(name, value);
        return this;
    }

    public HttpCustomConnect cookie(String name, String value) {
        req.cookie(name, value);
        return this;
    }

    public HttpCustomConnect cookie(Map<String, String> cookies) {
        if (null == cookies) {
            return this;
        }
        for (Map.Entry<String, String> entity : cookies.entrySet()) {
            req.cookie(entity.getKey(), entity.getValue());
        }
        return this;
    }

    public HttpCustomConnect charSet(String charSet) {
        req.setCharset(charSet);
        return this;
    }

    public HttpCustomConnect isJson(boolean isJson) {
        req.setJson(isJson);
        return this;
    }

    public HttpCustomConnect proxy(HttpHost proxy) {
        req.proxy(proxy);
        return this;
    }

    public HttpCustomConnect timeout(int timeout) {
        req.timeout(timeout);
        return this;
    }

    public HttpCustomConnect autoRedirect(boolean autoRedirect) {
        req.setAutoRedirect(autoRedirect);
        return this;
    }

    public HttpCustomConnect cookieSpecs(String cookieSpecs) {
        req.setCookieSpecs(cookieSpecs);
        return this;
    }

    public HttpCustomConnect context(HttpContext httpContext) {
        req.setHttpContext(httpContext);
        return this;
    }

    public HttpCustomConnect ignoreCodes(String ignoreCodes) {
        req.ignoreCode(ignoreCodes);
        return this;
    }

    public HttpCustomConnect responseHandler(HttpCustomResponseHandler responseHandler) {
        req.responseHandler = responseHandler;
        return this;
    }


    private static class Request {
        private CloseableHttpClient client;
        private String url;
        private Map<String, String> data;
        private Map<String, String> headers;
        private Map<String, String> cookies;
        private Method method = Method.GET;
        private boolean isJson = false;
        private HttpHost proxy;
        private int timeout = 60000;
        private boolean autoRedirect = true;
        private String cookieSpecs = CookieSpecs.STANDARD;
        private String ignoreCodes;
        private HttpCustomResponseHandler responseHandler;
        private HttpContext httpContext;
        private String charset = "utf-8";

        public void httpClient(CloseableHttpClient client) {
            this.client = client;
        }

        public void url(String url) {
            this.url = url;
        }

        public boolean isJson() {
            return isJson;
        }

        public void setJson(boolean json) {
            isJson = json;
        }

        public void data(String name, String value) {
            if (null == data) {
                data = new HashMap<>();
            }
            this.data.put(name, value);
        }

        public void method(Method method) {
            this.method = method;
        }

        public Method getMethod() {
            return this.method;
        }

        public void header(String name, String value) {
            if (null == this.headers) {
                headers = new HashMap<>();
            }
            this.headers.put(name, value);
        }

        public void cookie(String name, String value) {
            if (null == cookies) {
                cookies = new HashMap<>();
            }
            this.cookies.put(name, value);
        }

        public CloseableHttpClient getClient() {
            return client;
        }

        public String getUrl() throws IOException {
            StringBuilder urlStr = new StringBuilder();
            urlStr.append(this.url);

            if (Method.POST.equals(method) || (Method.GET.equals(method) && null == data)) {
                return urlStr.toString();
            }

            urlStr.append("?");
            for (Map.Entry<String, String> entry : data.entrySet()) {
                urlStr.append(URLEncoder.encode(CommonUtil.getString(entry.getKey()), "UTF-8"));
                urlStr.append("=");
                urlStr.append(URLEncoder.encode(CommonUtil.getString(entry.getValue()), "UTF-8"));
                urlStr.append("&");
            }

            return urlStr.toString();
        }

        public Map<String, String> getData() {
            return data;
        }

        Map<String, String> getHeaders() {
            Map<String, String> header = new HashMap<>();
            header.put("connection", "Keep-Alive");
            header.put("Accept-Encoding", "gzip");
            if (null != headers) {
                header.putAll(headers);
            }
            if (null != cookies) {
                header.putAll(getCookies());
            }
            return header;
        }

        Map<String, String> getCookies() {
            Map<String, String> cookie = new HashMap<>();
            StringBuilder sb = new StringBuilder();
            if (null == cookies) {
                return null;
            }

            for (Map.Entry<String, String> entry : cookies.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append("; ");
            }
            cookie.put("cookie", sb.toString());
            return cookie;
        }

        void proxy(HttpHost proxy) {
            this.proxy = proxy;
        }

        HttpHost getProxy() {
            return proxy;
        }

        void timeout(int timeout) {
            if (timeout < 0) {
                timeout = 60;
            }
            this.timeout = timeout;
        }

        void setAutoRedirect(boolean autoRedirect) {
            this.autoRedirect = autoRedirect;
        }

        void setCookieSpecs(String cookieSpecs) {
            this.cookieSpecs = cookieSpecs;
        }

        void setHttpContext(HttpContext httpContext) {
            this.httpContext = httpContext;
        }

        void ignoreCode(String ignoreCodes) {
            this.ignoreCodes = ignoreCodes;
        }

        void responseHandler(HttpCustomResponseHandler httpCustomResponseHandler) {
            this.responseHandler = httpCustomResponseHandler;
        }

        HttpCustomResponseHandler getResponseHandler() {
            return this.responseHandler;
        }

        void setCharset(String charset) {
            this.charset = charset;
        }

        String getCharset() {
            return this.charset;
        }

    }


    @Data
    public static class Response {
        private Map<String, String> cookies;
        private String body;
        private Header[] headers;
        private HttpEntity entity;
    }


    public Response execute() throws IOException, HttpException {
        if (req.getMethod().equals(Method.GET)) {
            return sendGetAndGetResponse(req);
        } else {
//            return sendAndPostResponse(req);
            return null;
        }
    }


    /**
     * 发送Get请求
     *
     * @param client
     * @param url
     * @param headers
     * @return
     * @throws HttpException
     */
    public static Response sendGetAndGetResponse(Request req) throws IOException, HttpException {
        CloseableHttpClient client = (null == req.getClient() ? HttpClients.createDefault() : req.getClient());
        String url = req.getUrl();
        Map<String, String> headers = req.getHeaders();
        HttpHost proxy = req.getProxy();
        int timeout = req.timeout < 0 ? 60000 : req.timeout;
        boolean autoRedirect = req.autoRedirect;
        String cookieSpecs = req.cookieSpecs;
        List<String> ignoreCodes = CommonUtil.isEmptyStr(req.ignoreCodes) ? new ArrayList<>() : Arrays.asList(req.ignoreCodes.split(","));
        HttpContext httpContext = req.httpContext;
        if (null == client) {
            log.error("send http get request error,HttpClient is null");
            return null;
        }
        HttpEntity entity = null;
        CloseableHttpResponse response = null;
        HttpGet request = null;
        try {
            request = new HttpGet(url);
            RequestConfig config = httpRequestConfig(timeout, autoRedirect, cookieSpecs, proxy);
            request.setConfig(config);

            if (null != headers) {
                for (String key : headers.keySet()) {
                    request.setHeader(key, headers.get(key));
                }
            }


            response = client.execute(request, httpContext);
            int status = response.getStatusLine().getStatusCode();
            if ((status < 200 || status >= 400) && ignoreCodes.stream().noneMatch(ignoreCode -> status == Integer.parseInt(ignoreCode))) {
                throw new HttpException(status, status + ":" + response.getStatusLine().getReasonPhrase());
            }

            return handleResponse(response, req);

        } catch (HttpException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpException(500, e.getMessage());
        } finally {
            close(entity, response, request);
        }
    }


    private static Response handleResponse(CloseableHttpResponse response, Request req) throws IOException {
        Response custResponse = new Response();
        HttpEntity entity = null;
        custResponse.setBody(EntityUtils.toString(entity = response.getEntity(), req.getCharset()));
        custResponse.setCookies(getCookieFromGetHttpResponse(response));
        custResponse.setEntity(response.getEntity());
        return custResponse;
    }

    /**
     * 从response获取cookies
     *
     * @param response
     * @return
     */
    public static Map<String, String> getCookieFromGetHttpResponse(CloseableHttpResponse response) {
        Map<String, String> cookiesMap;
        Header[] cookies = response.getAllHeaders();
        if (null == cookies || cookies.length == 0) {
            return null;
        }

        cookiesMap = new HashMap<>();
        for (Header cookie : cookies) {
            if (!"Set-Cookie".equalsIgnoreCase(cookie.getName())) {
                continue;
            }
            String value = cookie.getValue().split(";")[0].trim();

            if (value.split("=").length >= 2) {
                int index = value.indexOf("=");
                cookiesMap.put(value.substring(0, index), value.substring(index + 1));

            }

        }

        return cookiesMap;
    }


    /**
     * 关闭流
     *
     * @param entity
     * @param response
     * @param request
     */

    private static void close(HttpEntity entity, CloseableHttpResponse response, HttpRequestBase request) {
        if (null != entity) {
            try {
                EntityUtils.consume(entity);
            } catch (IOException e) {
                log.warn("", e);
            }
        }
        if (null != response) {
            try {
                response.close();
            } catch (IOException e) {
                log.warn("", e);
            }
        }
        if (null != request) {
            request.releaseConnection();
        }
    }


    private static RequestConfig httpRequestConfig(int timeout, boolean autoRedirect, String cookieSpecs, HttpHost proxy) {
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).setConnectTimeout(timeout);
        requestConfigBuilder.setCookieSpec(cookieSpecs);
        requestConfigBuilder.setRedirectsEnabled(autoRedirect);
        requestConfigBuilder.setProxy(proxy);

        return requestConfigBuilder.build();
    }


}
