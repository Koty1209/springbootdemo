package com.wenli.springbootdemo.common;

import lombok.Data;

/**
 * @program: springbootdemo
 * @description: 自定义响应类
 * @author: Koty
 * @create: 2019-07-11 16:39
 **/
@Data // get and set
public class MyResponse {

    private int code;// 状态码

    private String msg;// 信息

    private Object content;// 传递的内容

    // 全参构造器
    public MyResponse(int code, String msg, Object content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public MyResponse() {
    }

    /**
     * 自定义响应成功类
     * @param content 响应成功时要传递的内容
     * @return myResponse 返回自身，链式函数
     */
    public static MyResponse success(Object content){
        MyResponse myResponse = new MyResponse();
        myResponse.setCode(HttpCode.SUCCESS.getCode());// 状态码
        myResponse.setMsg(HttpCode.SUCCESS.getMsg());// 信息
        myResponse.setContent(content);

        return myResponse;
    }

    /**
     * 自定义响应失败类
     * @return myResponse 返回自身，链式函数
     */
    public static MyResponse error(){
        MyResponse myResponse = new MyResponse();
        myResponse.setCode(HttpCode.ERROR.getCode());// 状态码
        myResponse.setMsg(HttpCode.ERROR.getMsg());// 信息
        myResponse.setContent(null);// 失败时不传递内容

        return myResponse;
    }

    /**
     * 拿到自定义异常，进行相应的处理和修改并响应
     * @param ex 自定义异常
     * @return 返回自定义响应
     */
    public static MyResponse wrapper(MyException ex){
        MyResponse myResponse = new MyResponse();
        myResponse.setCode(ex.getCode());
        myResponse.setMsg(ex.getMsg());
        myResponse.setContent(null);

        return myResponse;
    }

    // 自定义响应msg（信息）
    public MyResponse msg(String msg){
        this.msg = msg;
        return this;
    }

}
