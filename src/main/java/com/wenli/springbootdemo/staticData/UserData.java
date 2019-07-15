package com.wenli.springbootdemo.staticData;

import com.wenli.springbootdemo.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: springbootdemo
 * @description: 一个用于存储用户信息的类（不访问数据库的形式，先模拟数据库）
 * @author: Koty
 * @create: 2019-07-11 11:01
 **/
public class UserData {

    public static List<User> users;

    static {

        users = new ArrayList<User>();
        User user1 = new User();
        User user2 = new User();

        user1.setUsername("Ross");
        user1.setPassword("123");

        user2.setUsername("Joey");
        user2.setPassword("456");

        users.add(user1);
        users.add(user2);

    }

}
