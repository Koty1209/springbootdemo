package com.wenli.springbootdemo.common;

import lombok.Data;

/**
 * @program: springbootdemo
 * @description: 自定义异常类，自定义并统一异常，继承运行时异常
 * @author: Koty
 * @create: 2019-07-11 16:26
 **/
@Data
public class MyException extends RuntimeException {


    private int code;// 状态码

    private String msg;// 异常信息

    /**
     * 构造器
     * @param httpCode 自定义的异常枚举类
     */
    public MyException(HttpCode httpCode) {
        this.code = httpCode.getCode();
        this.msg = httpCode.getMsg();
    }

    /**
     *一个自定义异常信息的方法
     * @param msg 自定义异常信息
     * @return this 返回自身（链式函数）
     */
    public MyException msg(String msg){
        this.msg = msg;
        return this;
    }



}
