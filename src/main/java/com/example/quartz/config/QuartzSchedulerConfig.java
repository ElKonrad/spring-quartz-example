package com.example.quartz.config;

import com.example.quartz.job.TestJob;
import lombok.RequiredArgsConstructor;
import org.joda.time.LocalDateTime;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class QuartzSchedulerConfig {

    private final DataSource dataSource;
    private final ApplicationContext context;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {

        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);

        factory.setQuartzProperties(quartzProperties());
        factory.setTriggers(sampleJobTrigger());
        factory.setJobFactory(jobFactory());

        return factory;
    }

    @Bean
    public JobFactory jobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    public Trigger sampleJobTrigger() {

        CustomCronTriggerFactoryBean factoryBean = new CustomCronTriggerFactoryBean();
        factoryBean.setJobDetail(sampleJobDetail());
        factoryBean.setName("test-trigger");
        factoryBean.setStartTime(LocalDateTime.now().toDate());
        factoryBean.setEndTime(LocalDateTime.now().plusMinutes(1).toDate());
        factoryBean.setCronExpression("0/10 * * * * ?");
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

        try {
            factoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        CronTrigger object = factoryBean.getObject();

        return object;
    }

    public JobDetail sampleJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();

        factoryBean.setJobClass(TestJob.class);
        factoryBean.setDurability(true);
        factoryBean.setApplicationContext(context);
        factoryBean.setName("test-job");

        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }
}
