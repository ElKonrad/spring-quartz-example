package com.example.quartz;

import com.example.quartz.business.Task;
import com.example.quartz.business.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@SpringBootApplication
public class QuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(TaskService taskService) {
        return args -> {
            taskService.save(new Task("Create Spring Boot Application", Date.from(LocalDate.of(2018,5, 7).atStartOfDay(ZoneId.systemDefault()).toInstant())));
            taskService.save(new Task("Create Spring Project Packages", Date.from(LocalDate.of(2018,5, 7).atStartOfDay(ZoneId.systemDefault()).toInstant())));
            taskService.save(new Task("Create the Task Domain Class", Date.from(LocalDate.of(2018,5, 7).atStartOfDay(ZoneId.systemDefault()).toInstant())));
            taskService.save(new Task("Create service and repository classes", Date.from(LocalDate.of(2018,5, 7).atStartOfDay(ZoneId.systemDefault()).toInstant())));
            taskService.save(new Task("Create the command line runner to load data", Date.from(LocalDate.of(2018,5, 10).atStartOfDay(ZoneId.systemDefault()).toInstant())));
            taskService.save(new Task("Create the required configuration properties", Date.from(LocalDate.of(2018,5, 10).atStartOfDay(ZoneId.systemDefault()).toInstant())));
            taskService.save(new Task("Run the Spring Boot Application", Date.from(LocalDate.of(2018,5, 10).atStartOfDay(ZoneId.systemDefault()).toInstant())));
            taskService.save(new Task("Check the H2 Console for the initial data", Date.from(LocalDate.of(2018,5, 10).atStartOfDay(ZoneId.systemDefault()).toInstant())));
        };
    }
}
