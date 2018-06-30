package com.haotu369.util.schedule.task;

import java.util.concurrent.Callable;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/6/30
 */
public class SixTask implements Callable {

    @Override
    public Object call() throws Exception {
        return "执行完任务6";
    }
}
