package com.haotu369.util.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/6/30
 */
public interface Task {
    void doTask() throws ExecutionException, InterruptedException;
}
