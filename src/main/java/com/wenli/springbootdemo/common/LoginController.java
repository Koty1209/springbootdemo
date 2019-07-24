package com.wenli.springbootdemo.common;

import com.wenli.springbootdemo.model.User;
import com.wenli.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
@CrossOrigin
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public Object user(@RequestBody User user){
        User u = userService.login(user);

        return u!=null?MyResponse.success(u):MyResponse.error().msg("登录失败");
    }




}
