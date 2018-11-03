package com.magicnian.quartz.springbootquartz.util.http;

import com.magicnian.quartz.springbootquartz.config.HttpRequestRetryHandlerImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
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
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

/**
 * httpclient工厂类
 *
 * @Auther: liuniannian
 * @Date: 2018/11/3 14:42
 * @Description:
 */
@Slf4j
public class HttpClientFactory {

    /**
     * client池管理器
     */
    private static PoolingHttpClientConnectionManager manager;

    static {
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, (a, b) -> true).build();
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", sslSocketFactory)
                    .build();

            manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            manager.setDefaultMaxPerRoute(200);
            manager.setMaxTotal(200);
            //handshake timeout，采用默认值60s
            manager.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(60000).build());

        } catch (Exception e) {
            log.error("warn happened init PoolingHttpClientConnectionManager:{}", e);
        }
    }

    private HttpClientFactory() {

    }

    public CloseableHttpClient getInstance(HttpClientConfig config) {
        HttpClientBuilder clientBuilder = HttpClients.custom();
        clientBuilder.setConnectionManager(manager);

        //请求重试
        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandlerImpl(config.getRetryTimes());
        clientBuilder.setRetryHandler(retryHandler);

        clientBuilder.setUserAgent(config.getUserAgent());

        if (config.isUseLaxRedirectStrategy()) {
            clientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
        }

        RequestConfig.Builder requestBuilder = RequestConfig.custom()
                .setConnectionRequestTimeout(30000)
                .setSocketTimeout(30000)
                .setConnectTimeout(30000)
                .setRedirectsEnabled(config.isAutoRedirect());

        if (config.getProxy() != null) {
            requestBuilder.setProxy(config.getProxy());
        }

        clientBuilder.setDefaultRequestConfig(requestBuilder.build());

        return clientBuilder.build();
    }
}
