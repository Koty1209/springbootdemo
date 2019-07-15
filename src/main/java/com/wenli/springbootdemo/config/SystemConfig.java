package com.wenli.springbootdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: springbootdemo
 * @description: 自定义配置文件实体类
 * @author: Koty
 * @create: 2019-07-11 12:56
 **/
@Component
@ConfigurationProperties(prefix = "app")
@Data
public class SystemConfig {

    private String info;
    private String author;
    private String email;
    private String swaggerTitle;
    private String swaggerContactName;
    private String swaggerContactWebUrl;
    private String swaggerContactEmail;
    private String swaggerVersion;
    private String swaggerDescription;
    private String swaggerTermsOfServiceUrl;





}
