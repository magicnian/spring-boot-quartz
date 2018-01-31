package com.magicnian.quartz.springbootquartz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * email配置类
 * Created by liunn on 2018/1/26.
 */
@Component
@ConfigurationProperties(prefix ="email")
@PropertySource("classpath:email.properties")
@Data
public class EmailConfig {
    private String myaccount;

    private String mypassword;

    private String sendaccount;

    private String smtphost;
}
