package com.magicnian.quartz.springbootquartz.bean;

import com.magicnian.quartz.springbootquartz.annotation.ParamValidator;
import lombok.Data;

/**
 * 站点信息
 * Created by liunn on 2018/2/7.
 */
@Data
public class Site {

    /**
     * 站点id（唯一）
     */
    @ParamValidator(required = true)
    private String siteId;

    /**
     * 站点的url
     */
    @ParamValidator(required = true)
    private String url;

    /**
     * 处理job的全称
     */
    @ParamValidator(required = true)
    private String jobName;

    /**
     * 定时器cron表达式
     */
    @ParamValidator(required = true)
    private String cron;
}
