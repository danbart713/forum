package com.dvb.forum.config;

import com.dvb.forum.scheduler.TokenBlacklistCleanupJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.util.Map;
import java.util.Properties;

@Configuration
@Slf4j
public class SpringQuartzSchedulerConfig {

    @Bean
    public SpringBeanJobFactory springBeanJobFactory(ApplicationContext applicationContext) {
        AutoWiringSpringBeanJobFactory autoWiringSpringBeanJobFactory = new AutoWiringSpringBeanJobFactory();

        autoWiringSpringBeanJobFactory.setApplicationContext(applicationContext);
        return autoWiringSpringBeanJobFactory;
    }

    @Bean
    @ConfigurationProperties("scheduler")
    public Properties schedulerProperties() {
        return new Properties();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(Map<String, Trigger> triggerMap, Map<String, JobDetail> jobDetailMap,
                                                     ApplicationContext applicationContext) {
        Trigger[] triggerArray = triggerMap.values().toArray(new Trigger[0]);
        JobDetail[] jobDetailArray = jobDetailMap.values().toArray(new JobDetail[0]);

        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setQuartzProperties(schedulerProperties());
        schedulerFactoryBean.setJobFactory(springBeanJobFactory(applicationContext));
        schedulerFactoryBean.setJobDetails(jobDetailArray);
        schedulerFactoryBean.setTriggers(triggerArray);

        return schedulerFactoryBean;
    }

    @Bean("tokenBlacklistCleanup")
    public JobDetailFactoryBean tokenBlacklistCleanupJobDetailFactoryBean() {
        Properties schedulerProperties = schedulerProperties();

        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(TokenBlacklistCleanupJob.class);
        jobDetailFactoryBean.setName(schedulerProperties.getProperty("token-blacklist-cleanup.jobDetailFactoryBean.name"));
        jobDetailFactoryBean.setDescription(schedulerProperties.getProperty("token-blacklist-cleanup.jobDetailFactoryBean.description"));
        jobDetailFactoryBean.setDurability(Boolean.parseBoolean(schedulerProperties.getProperty("token-blacklist-cleanup.jobDetailFactoryBean.durability")));

        return jobDetailFactoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean tokenBlacklistCleanupSimpleTriggerFactoryBean(@Qualifier("tokenBlacklistCleanup") JobDetail jobDetail) {
        Properties schedulerProperties = schedulerProperties();

        SimpleTriggerFactoryBean simpleTriggerFactoryBean = new SimpleTriggerFactoryBean();
        simpleTriggerFactoryBean.setJobDetail(jobDetail);
        simpleTriggerFactoryBean.setName(schedulerProperties.getProperty("token-blacklist-cleanup.simpleTriggerFactoryBean.name"));
        simpleTriggerFactoryBean.setRepeatInterval(Long.parseLong(schedulerProperties.getProperty("token-blacklist-cleanup.simpleTriggerFactoryBean.repeat-interval")));
        simpleTriggerFactoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);

        return simpleTriggerFactoryBean;
    }

}
