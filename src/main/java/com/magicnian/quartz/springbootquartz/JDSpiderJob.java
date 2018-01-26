package com.magicnian.quartz.springbootquartz;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 显卡，内存，cpu，显示器 京东价格爬虫
 * Created by liunn on 2018/1/25.
 */
@Service
@Slf4j
public class JDSpiderJob {

//    private static Map<String,String> headers = new HashMap<>();

    /**
     * 关键词：内存条
     * 搜索条件：1.DDR4 2400
     * 2.单套容量 8GB
     * 3.台式机内存
     */
    private static final String url1 = "https://search.jd.com/search?keyword=%E5%86%85%E5%AD%98%E6%9D%A1&enc=utf-8&ev=123_76441%5E5181_76033%5E210_1558%5E";

//    static {
//        headers.put("Host","search.jd.com");
//        headers.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
//    }

    @Scheduled(cron = "0 0 20 * * ? ")
    public void spider() {
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url1);
            httpGet.setHeader("Host", "search.jd.com");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:53.0) Gecko/20100101 Firefox/53.0");
            CloseableHttpResponse response = client.execute(httpGet);
            if (200 == response.getStatusLine().getStatusCode()) {
                String content = EntityUtils.toString(response.getEntity());
                Document document = Jsoup.parse(content);
                Elements elements = document.select("#J_goodsList > ul > li > div > div.p-price > strong > i");
                log.info("items size:{}", elements.size());
                float totalPrice = 0.0f;
                for (Element element : elements) {
                    String priceStr = element.text();
                    float price = Float.valueOf(priceStr);
                    totalPrice += price;
                }

                float avargePrice = totalPrice / elements.size();
                log.info("avargePrice:{}", avargePrice);
//                Collections.sort(prices);
                SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
                Date date = new Date();
                String dateStr = sdf.format(date);
                String text = "京东商城最新内存条均价：" + avargePrice + "  日期：" + dateStr;
                EmailUtil.sendEmail(text);
            } else {
                log.error("response code:{}", response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            log.error("get price error:{}", e);
        }

    }

}
