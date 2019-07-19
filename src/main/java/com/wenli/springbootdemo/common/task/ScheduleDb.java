//package com.wenli.springbootdemo.common.task;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.stereotype.Component;
//
///**
// * @program: springbootdemo
// * @description: 动态实现实时生效的定时任务，基于数据库，接口
// * @author: Koty
// * @create: 2019-07-15 11:53
// **/
//
//@Component
//@Configuration
//@EnableScheduling   // 开启定时任务
//@Slf4j
////@Mapper
//public class ScheduleDb implements SchedulingConfigurer {
//
//    @Mapper
//    public interface CronMapper {
//        @Select("select cron_expression from cron where id=#{id}")
//        public String getCronById(String id);
//    }
//
//    @Autowired
//    CronMapper cronMapper;
//
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
//        scheduledTaskRegistrar.addTriggerTask(
//                ()->log.info("执行动态定时任务"), //定时任务执行的内容
//                triggerContext -> { // 定时任务执行的时间  从数据库动态获取
//                    String cron = cronMapper.getCronById("execute");
//                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
//                }
//        );
//    }
//
//
//}
