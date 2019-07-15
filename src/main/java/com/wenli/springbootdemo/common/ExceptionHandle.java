package com.wenli.springbootdemo.common;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *自定义异常捕捉类，并自定义一些处理
 * 适用于：前后端分离作业时，前端遇到错误时看不懂后端异常，自定义一套异常给前端人员调试
 */
@ControllerAdvice // 异常处理
public class ExceptionHandle {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handleException(Exception e){ //
//        e.printStackTrace();
        if (e instanceof MyException) { // 如果该异常在自定义异常内（code:200,500）
            return MyResponse.wrapper((MyException) e);
        }else if (e instanceof ArithmeticException){ // 算术异常
            return MyResponse.error().msg("这是一个算术异常");
        }else if (e instanceof MethodArgumentNotValidException) { // 方法参数无效异常
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException)e;
            // 将方法参数无效异常的信息给到响应中去
            return MyResponse.error().msg("方法参数无效，请检查：" +
                    exception.getBindingResult().getFieldError().getDefaultMessage());
        }else if (e instanceof HttpRequestMethodNotSupportedException) { // http请求方法不支持异常
            HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException)e;
            // 将http请求方法不支持异常的信息给到响应中去
            return MyResponse.error().msg("http请求方式可能发生错误，请检查：" + exception.getMessage());
        }else if (e instanceof HttpMessageNotReadableException) { // HttpMessage不可读异常
            HttpMessageNotReadableException exception = (HttpMessageNotReadableException)e;
            // 将HttpMessage不可读异常的信息给到响应中去
            return MyResponse.error().msg("json数据格式可能发生错误，请检查：" + exception.getMessage());
        }

        // 如果不在上述情况内，修改msg
        return MyResponse.error().msg("这是一个未知异常，请联系相关人员");
    }

}
