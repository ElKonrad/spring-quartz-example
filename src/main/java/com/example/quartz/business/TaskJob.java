package com.example.quartz.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskJob {

    private final TaskService taskService;

    public void sendNotification() {
        List<Task> startedTasks = taskService.getStartedTasks();
        startedTasks.forEach(task -> log.info(task.toString()));
    }
}