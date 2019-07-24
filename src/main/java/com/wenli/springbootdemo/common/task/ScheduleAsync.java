//package com.wenli.springbootdemo.common.task;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * @program: springbootdemo
// * @description: 多线程定时任务
// * @author: Koty
// * @create: 2019-07-15 19:18
// **/
//
//@Component
//@EnableAsync
//@Slf4j
//public class ScheduleAsync {
//
//    @Async
//    @Scheduled(fixedRate = 500)
//    public void task1() throws InterruptedException {
//        Thread.sleep(2000);
//        log.info(Thread.currentThread().getName()+"在执行任务");
//    }
//
//    @Async
//    @Scheduled(fixedRate = 500)
//    public void task2() throws InterruptedException {
//        Thread.sleep(2000);
//        log.info(Thread.currentThread().getName()+"在执行任务");
//    }
//
//
//}
