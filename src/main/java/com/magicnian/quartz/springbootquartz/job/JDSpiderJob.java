package com.magicnian.quartz.springbootquartz.job;

import com.magicnian.quartz.springbootquartz.config.HttpConfig;
import com.magicnian.quartz.springbootquartz.util.EmailUtil;
import com.magicnian.quartz.springbootquartz.util.HttpClientPool;
import com.magicnian.quartz.springbootquartz.util.HttpCustomConnect;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 显卡，内存，cpu，显示器 京东价格爬虫
 * Created by liunn on 2018/1/25.
 */
@Service
@Slf4j
public class JDSpiderJob {


    @Autowired
    private EmailUtil emailUtil;

    private static HttpConfig httpConfig;

    /**
     * 关键词：内存条
     * 搜索条件：1.DDR4 2400
     * 2.单套容量 8GB
     * 3.台式机内存
     */
    private static final String url1 = "https://search.jd.com/search?keyword=%E5%86%85%E5%AD%98%E6%9D%A1&enc=utf-8&ev=123_76441%5E5181_76033%5E210_1558%5E";

    private static final String url2 = "https://search.jd.com/search?keyword=显卡&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=xianka&ev=296_87720%5E24_81974%5E&uc=0";

    static {
//        httpConfig = new HttpConfig("jd-ram");
        httpConfig = new HttpConfig();
        httpConfig.setConfigId("jd-spider");
        httpConfig.setRetryTimes(3);
    }

//    @Scheduled(cron = "0 0 20 * * ? ")
    public void spider() {
        try {

            String ramprice = ramSpider();

            Map<String, String> gpus = gpuSpider();

            String content = genrateContent(ramprice, gpus);

            emailUtil.sendEmail(content);


        } catch (Exception e) {
            log.error("get price error:{}", e);
        }

    }

    private String ramSpider() throws Exception {
        CloseableHttpClient client = HttpClientPool.getClient(httpConfig);
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "search.jd.com");
        HttpCustomConnect httpCustomConnect = HttpCustomConnect.custom().connect(url1).httpClient(client).headers(headers).get();
        HttpCustomConnect.Response response = httpCustomConnect.execute();

        Document document = Jsoup.parse(response.getBody());
        Elements elements = document.select("#J_goodsList > ul > li > div > div.p-price > strong > i");
        log.info("ram items size:{}", elements.size());
        float totalPrice = 0.0f;
        for (Element element : elements) {
            String priceStr = element.text();
            float price = Float.valueOf(priceStr);
            totalPrice += price;
        }

        float avargePrice = totalPrice / elements.size();
        log.info("avargePrice:{}", avargePrice);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
//        Date date = new Date();
//        String dateStr = sdf.format(date);

        return String.valueOf(avargePrice);
    }


    private Map<String, String> gpuSpider() throws Exception {
        Map<String, String> resultMap = new HashMap<>();
        CloseableHttpClient client = HttpClientPool.getClient(httpConfig);
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", "search.jd.com");
        HttpCustomConnect httpCustomConnect = HttpCustomConnect.custom().connect(url2).httpClient(client).headers(headers).get();
        HttpCustomConnect.Response response = httpCustomConnect.execute();

        Document document = Jsoup.parse(response.getBody());

        Elements elements = document.select("#J_goodsList > ul > li > div");
        log.info("gpu items size:{}", elements.size());

        int size = elements.size() > 5 ? 5 : elements.size();
        for (int i = 0; i < size; i++) {
            String gpuName = "";
            String gpuPrice = "";
            Element nameElement = elements.get(i).select("div.p-name.p-name-type-2 > a > em").first();
            if (null != nameElement) {
                gpuName = nameElement.text();
            }

            Element priceElement = elements.get(i).select("div.p-price > strong > i").first();

            if (null != priceElement) {
                gpuPrice = priceElement.text();
            }

            resultMap.put(gpuName, gpuPrice);
        }


        return resultMap;
    }

    private String genrateContent(String ramPrice, Map<String, String> gpus) throws Exception {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("templates/price.html");
        byte[] bytes = IOUtils.toByteArray(inputStream);
        String content = new String(bytes, "UTF-8");

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        content = content.replace("${date}", sdf.format(date));

        content = content.replace("${ramprice}", ramPrice);
        Set<String> keys = gpus.keySet();
        Iterator<String> iterator = keys.iterator();
        for (int i = 0; i < 5; i++) {
            String name = iterator.next();
            content = content.replace("${gpuname" + i + "}", name).replace("${gpuprice" + i + "}", gpus.get(name));
        }

        log.info("content:{}", content);

        return content;
    }

}
