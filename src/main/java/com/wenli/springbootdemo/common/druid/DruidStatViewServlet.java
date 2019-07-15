package com.wenli.springbootdemo.common.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-12 14:41
 **/
@WebServlet(urlPatterns = "/druid/*", initParams = {
        @WebInitParam(name = "allow", value = ""),// 访问白名单
        @WebInitParam(name = "deny", value = ""),// 访问黑名单
        @WebInitParam(name = "resetEnble", value = "true")
})
public class DruidStatViewServlet extends StatViewServlet {
}
