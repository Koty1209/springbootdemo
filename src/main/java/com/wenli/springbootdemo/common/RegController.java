package com.wenli.springbootdemo.common;

import com.wenli.springbootdemo.model.User;
import com.wenli.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("register")
@CrossOrigin
public class RegController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public Object user(@RequestBody @Valid User user){

        User u = userService.register(user);

        return u!=null?MyResponse.success(u).msg("注册成功"):MyResponse.error().msg("注册失败");
    }

    /**
     * 用户注册时判断用户名是否已存在，用查询做，注册不需要token所以在RegController里实现该方法
     */
    @PostMapping("/usernameIsReged/{username}")
    public Object usernameIsReged(@RequestBody @PathVariable("username") String username){
        User user = userService.getUserByUsername(username);

        return user!=null?MyResponse.error().msg("该用户名已被占用"):MyResponse.success(null).msg("该用户名可以使用");
    }

}
