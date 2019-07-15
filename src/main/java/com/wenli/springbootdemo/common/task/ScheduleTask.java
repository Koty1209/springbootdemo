package com.wenli.springbootdemo.common.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: springbootdemo
 * @description: springboot实现定时任务
 * @author: Koty
 * @create: 2019-07-15 11:28
 **/

@Component
@Configuration  // 用于标记配置类，兼备Component效果
@EnableScheduling   // 开启定时任务
@Slf4j
public class ScheduleTask {

    // 添加定时任务
    @Scheduled(cron = "0/5 * * * * ?")  // 秒 分 时 日 月 周
    // 或直接指定时间间隔，例如：5秒
    // @Scheduled(fixedRate = 5000)
    public void task1(){
        log.info("我是task1");
    }


}
