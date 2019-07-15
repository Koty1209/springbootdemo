package com.wenli.springbootdemo.common;

import lombok.Data;

/**
 * 自定义并枚举http异常
 */
public enum HttpCode {

    /**
     * 枚举状态：
     * SUCCESS：访问成功，构造并赋值
     * ERROR：访问失败，构造并赋值
     */
    SUCCESS(200,"访问成功"),
    ERROR(500,"访问失败，服务器出错了。。。")
    ;


    private int code;// 状态码

    private String msg;// message 要显示（传递）的信息

    // 全参构造方法
    HttpCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
