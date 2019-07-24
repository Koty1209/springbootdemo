package com.wenli.springbootdemo.common;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtFilter extends BasicHttpAuthenticationFilter {


    // 处理跨域问题
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;

        rep.setHeader("Access-control-Allow-Origin", req.getHeader("Origin"));
        rep.setHeader("Access-control-Allow-Methods", "POST,GET,DELETE,PUT,OPTIONS");
        rep.setHeader("Access-control-Allow-Headers", "*");


        if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
            rep.setStatus(HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }


    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest req= (HttpServletRequest)request;

        String token = req.getHeader("ticket");

        JwtToken jwtToken =  new JwtToken(token);

        this.getSubject(request,response).login(jwtToken);

        return true;
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        try {
            executeLogin(request, response);
            return true;
        } catch (Exception e) {
//            throw new MyException(HttpCode.ERROR);
            return false;
        }


    }
}
