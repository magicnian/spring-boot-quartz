package com.magicnian.quartz.springbootquartz.launch;

import com.magicnian.quartz.springbootquartz.util.HttpConnectionMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by liunn on 2018/1/31.
 */
@Component
@Slf4j
public class Launcher implements ApplicationListener<ContextRefreshedEvent> {

    private static HttpConnectionMonitor httpConnectionMonitor;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Lanucher is starting!");
        httpConnectionMonitor = new HttpConnectionMonitor();
        httpConnectionMonitor.start();
    }
}
