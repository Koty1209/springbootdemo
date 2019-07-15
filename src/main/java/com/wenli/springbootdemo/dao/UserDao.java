package com.wenli.springbootdemo.dao;

import com.wenli.springbootdemo.model.User;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface UserDao {

    /**
     * @param user 实现多条件查询时需要条件参数，将user对象作为参数传进去
     */
//    @Select("select * from user")
    List<User> getAllUser(User user);

    User getUserById(int id);

    int addUser(User user);

    int deleteUserById(int id);

    int updateUser(User user);

}
