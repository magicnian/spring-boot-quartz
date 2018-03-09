package com.magicnian.quartz.springbootquartz.service;

import com.magicnian.quartz.springbootquartz.bean.Site;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

/**
 * 爬虫job仓库
 * Created by liunn on 2018/2/9.
 */
@Slf4j
public class JobRepository {

    private static StdSchedulerFactory schedulerFactory;

    private static boolean inited = false;


    public static void init(int threadNum){
        try {
            schedulerFactory = new StdSchedulerFactory();

            Properties props = new Properties();
            props.put("org.quartz.scheduler.instanceId", "Spider-Master-Instance");
            props.put("org.quartz.scheduler.instanceName", "Spider-Master-Scheduler");
            props.put("org.quartz.threadPool.threadCount", threadNum + "");
            schedulerFactory.initialize(props);
            schedulerFactory.getScheduler().start();

            inited = true;
        }catch (SchedulerException e){
            log.info("init JobRepository error", e);
            inited = false;
        }
    }

    /**
     * 添加爬虫任务
     * @param site
     * @return
     */
    public static boolean add(Site site){
        if (!inited) {
            log.warn("JobRepository is not inited!");
            return false;
        }

        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            // 检查Job是否存在
            JobKey key = new JobKey(site.getSiteId());
            if (scheduler.checkExists(key)) {
                log.info("the job:{} is exists,skip add!", site.getSiteId());
                return false;
            }

            //构建trigger
            TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity("Trigger-" + site.getSiteId())
                    .withSchedule(CronScheduleBuilder.cronSchedule(site.getCron()).withMisfireHandlingInstructionIgnoreMisfires());

            Trigger trigger = triggerBuilder.build();

//            JobDetail job = initJob(site, site.getSiteDomain());

        }catch (Exception e){

        }
        return false;
    }



    private static JobDetail initJob(Site site, String jobName) throws Exception {
//        JobDetail job = JobBuilder.newJob().ofType(SiteTaskProducer.class).withIdentity(jobName).withDescription(jobName).build();

        // 设置任务参数.
//        job.getJobDataMap().put("site", site);
//        return job;
        return null;
    }
}
