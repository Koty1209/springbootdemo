package com.wenli.springbootdemo.controller;

import com.wenli.springbootdemo.common.MyResponse;
import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.User;
import com.wenli.springbootdemo.service.UserService;
import com.wenli.springbootdemo.staticData.UserData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-11 16:55
 **/
@Api(value = "user模块接口", description = "这是一个用户模块的接口")
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("查询所有用户")
    @PutMapping(value = "/getAllUser") // 这个查询需要带参数，GET请求无法实现（没有请求头）
//    @RequiresRoles("admin")
//    @RequiresPermissions("general")
    public Object getAllUser(@RequestBody PageParam<User> pageParam){
        return MyResponse.success(userService.getAllUser(pageParam));
    }

    @GetMapping(value = "/getUserById/{id}")
    public Object getUserById(@PathVariable("id") int id){
        /*User user = new User();
        for (int i = 0; i < UserData.users.size(); i++) {
            if (UserData.users.get(i).getId() == id) {
                user = UserData.users.get(i);
            }
        }*/
        if (userService.getUserById(id) != null) {
            return MyResponse.success(userService.getUserById(id)); // 成功，将查到的user放到响应内容(content)中去
        }

        return MyResponse.error();
    }

//    @Transactional
    @PostMapping("/addUser")
    public Object addUser(@RequestBody @Valid User user){
//        UserData.users.add(user);

        Object object = userService.addUser(user);
//        System.out.println(1 / 0);

        return MyResponse.success(object);
    }
//    @PostMapping("/addUser")
//    public Object addUser(@RequestBody @Valid User user){
//        User u=(User)userService.addUser(user);
//
//        return u!=null?MyRsp.success(u).
//                msg("添加成功"):MyRsp.error().msg("添加失败");
//    }

    /**
     * @PutMapping 中value的{}里带上参数名，通过@PathVariable来接收该参数值并取名给后面的形参
     */
    @PutMapping("/deleteUserById/{id}")
    public Object deleteUserById(@PathVariable("id") int id){
        for (int i = 0; i < UserData.users.size(); i++) {
            if (UserData.users.get(i).getId() == id) {
                UserData.users.remove(i);
            }
        }

        return MyResponse.success(userService.deleteUserById(id));
    }

    @PutMapping("/updateUser")
    public Object updateUser(@RequestBody  User user){

        user.setUsername(null); // 用户名无法修改
        User u = userService.getUserById(user.getId()); // 根据用户参数拿到用户id
        user.setIsActive(u.getIsActive()); // 前端不能传（修改）用户激活状态，保持激活状态为：根据id查到的用户的激活状态

        return MyResponse.success(userService.updateUser(user));
    }



}
