package com.wenli.springbootdemo.service;

import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.model.User;

import java.util.List;

public interface UserService {


    Object getAllUser(PageParam<User> pageParam);

    User getUserById(int id);

    Object addUser(User user);

    boolean deleteUserById(int id);

    boolean updateUser(User user);


}
