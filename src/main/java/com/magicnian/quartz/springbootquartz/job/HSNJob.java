package com.magicnian.quartz.springbootquartz.job;

import com.magicnian.quartz.springbootquartz.util.http.HttpClientConfig;
import com.magicnian.quartz.springbootquartz.util.http.HttpClientFactory;
import com.magicnian.quartz.springbootquartz.util.http.HttpClientResponse;
import com.magicnian.quartz.springbootquartz.util.http.HttpCrawler;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: liuniannian
 * @Date: 2018/11/5 15:05
 * @Description:
 */
@Component
@Slf4j
public class HSNJob {

    private static HttpClientConfig config;

    static {
        config = new HttpClientConfig();
        config.setAutoRedirect(true);
        config.setCookieSpec(CookieSpecs.STANDARD);
        config.setTimeout(30000);
    }


    public void run() {

        try {
            CloseableHttpClient client = HttpClientFactory.getInstance(config);

            String url = "https://www.imoney88.com/?user&q=check_phones";

            Map<String, String> data = new HashMap<>();
            data.put("phone", "18851631386");

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            HttpClientResponse response = HttpCrawler.custom().client(client).config(config).url(url).data(data).headers(headers).post().excute();

            log.info(response.getBodyStr());

        } catch (Exception e) {
            log.error("execute hsn get error:{}", e);
        }

    }

//    public static void main(String[] args) {
//        run();
//    }

}
