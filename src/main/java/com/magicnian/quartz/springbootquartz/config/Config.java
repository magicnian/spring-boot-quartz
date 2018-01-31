package com.magicnian.quartz.springbootquartz.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by liunn on 2018/1/25.
 */
@EnableScheduling
@Configuration
@ComponentScan(basePackages = {"com.magicnian.quartz.springbootquartz"})
public class Config {
}
