package com.wenli.springbootdemo.common;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author xiongzh
 * @date 2019-01-26
 * @comment shiro权限 是通过filter 实现 抛出的异常在filter中  此filter负责处理该异常  实现输出结果和异常的统一封装
 */
@Order(1) // 表示最先实例化
@WebFilter(filterName = "ShiroExceptionFilter",urlPatterns = {"/*"})
public class ShiroExceptionFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
        	e.printStackTrace();
            // 自定义异常的类，用户返回给客户端相应的JSON格式的信息
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            // 如果不是自定义的异常默认是shiro认证失败的异常
            String rspJson=(e instanceof MyException)?convertObjectToJson(MyResponse.wrapper((MyException)e)):
            	convertObjectToJson(MyResponse.wrapper((MyException)e));

            OutputStream out = response.getOutputStream();
            out.write(rspJson.getBytes("UTF-8"));
            out.flush();


        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}
