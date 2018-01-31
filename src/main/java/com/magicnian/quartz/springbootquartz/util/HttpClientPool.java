package com.magicnian.quartz.springbootquartz.util;

import com.magicnian.quartz.springbootquartz.config.HttpClientKeepAliveStrategy;
import com.magicnian.quartz.springbootquartz.config.HttpConfig;
import com.magicnian.quartz.springbootquartz.config.HttpRequestRetryHandlerImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * client连接池
 * Created by liunn on 2018/1/31.
 */
@Slf4j
public class HttpClientPool {


    /**
     * 每个config对应一个单独的httpclient缓存
     */
    private static Map<String, CloseableHttpClient> HTTPCLIENT_MAP = new ConcurrentHashMap<>();

    /**
     * client池管理器
     */
    private static PoolingHttpClientConnectionManager manager;


    static {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (arg0, arg1) -> true).build();
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);

            Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE).register("https", sslSocketFactory)
                    .build();

            manager = new PoolingHttpClientConnectionManager(reg);
            //handshake timeout，采用默认值60s
            manager.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(60000).build());
            manager.setDefaultMaxPerRoute(200);
            manager.setMaxTotal(200);
        } catch (Exception e) {
            log.warn("warn happened init PoolingHttpClientConnectionManager ", e);
        }
    }

    public static CloseableHttpClient getClient(HttpConfig config) {
        CloseableHttpClient client = HTTPCLIENT_MAP.get(config.getConfigId());
        return null == client ? generateClient(config) : client;
    }

    private static synchronized CloseableHttpClient generateClient(HttpConfig config) {

        // 请求重试处理
        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandlerImpl(config.getRetryTimes());

        HttpClientBuilder clientBuilder = HttpClients.custom().setConnectionManager(manager).setRetryHandler(retryHandler).setKeepAliveStrategy(new HttpClientKeepAliveStrategy());

        if (null != config.getUserAgent()) {
            clientBuilder.setUserAgent(config.getUserAgent());
        }

        if (config.useGzip()) {
            clientBuilder.addInterceptorFirst((HttpRequestInterceptor) (request, context) -> {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }
            });
        }

        if (config.isUseLaxRedirectStrategy()) {
            clientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
        }

        CloseableHttpClient client = clientBuilder.build();

        HTTPCLIENT_MAP.put(config.getConfigId(), client);

        return client;
    }


    /**
     * 获取所有的连接池管理器
     *
     * @return
     */
    public static PoolingHttpClientConnectionManager getConnMgr() {
        return manager;
    }

}
