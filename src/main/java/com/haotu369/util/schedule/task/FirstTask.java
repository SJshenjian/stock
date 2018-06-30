package com.haotu369.util.schedule.task;

import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/6/30
 */
public class FirstTask implements Callable {

    @Override
    public String call() throws Exception {
        return "执行完任务1";
    }
}
