package com.zhou;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
public class Test {

    @org.junit.jupiter.api.Test
    public void test() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("task1");
        Thread.sleep(500);
        stopWatch.stop();

        stopWatch.start("task2");
        Thread.sleep(800);
        stopWatch.stop();

        stopWatch.start("task2");
        Thread.sleep(300);
        stopWatch.stop();

        //打印任务的耗时统计
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.shortSummary());

        //任务信息总揽
        System.out.println("所有任务总耗时："+stopWatch.getTotalTimeMillis());
        System.out.println("任务总数："+stopWatch.getTaskCount());
    }

}
