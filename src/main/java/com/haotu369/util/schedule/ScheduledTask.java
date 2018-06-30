package com.haotu369.util.schedule;

import com.haotu369.util.schedule.task.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/6/30
 */
@Component
@EnableAsync
@EnableScheduling
public class ScheduledTask implements Task{
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);

    // 每隔5秒执行一次
    @Scheduled(cron = "*/10 * * * * *")
    @Override
    public void doTask() throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);

        String firstResult = (String) service.submit(new FirstTask()).get();
        if (firstResult.indexOf("执行完") != -1) {
            LOGGER.info("任务1执行完");
            String secondResult = (String)service.submit(new SecondTask()).get();

            if (secondResult.indexOf("执行完") != -1) {
                LOGGER.info("任务2执行完");
                Future threeFuture = service.submit(new ThreeTask());
                Future fourFuture = service.submit(new FourTask());
                Future fiveFuture = service.submit(new FiveTask());

                String threeResult = (String) threeFuture.get();
                String fourResult = (String) fourFuture.get();
                String fiveResult = (String) fiveFuture.get();

                if (threeResult.indexOf("执行完") != -1 && fourResult.indexOf("执行完") != -1 && fiveResult.indexOf("执行完") != -1) {
                    LOGGER.info("任务3,4,5执行完");
                    String sixResult = (String) service.submit(new SixTask()).get();
                    if (sixResult.indexOf("执行完") != -1 ) {
                        System.out.println("当日任务已全部完成");
                    }
                }
            }
        } else {
            LOGGER.error("执行任务1出现异常");
        }
    }
}
