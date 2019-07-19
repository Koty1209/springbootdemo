package com.wenli.springbootdemo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wenli.springbootdemo.common.PageParam;
import com.wenli.springbootdemo.dao.UserDao;
import com.wenli.springbootdemo.model.User;
import com.wenli.springbootdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: springbootdemo
 * @description:
 * @author: Koty
 * @create: 2019-07-11 19:19
 **/
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Cacheable(key = "#p0", value = "users")
    @Override
    public Object getAllUser(PageParam<User> pageParam) {

        log.info("走的是数据库");

        /**
         * 1.设置分页的页面属性
         * pagehelper插件中的开始页设置，这里传了一个（第几页）pageNum，和一个（页尺寸）pageSize
         */
        PageHelper.startPage(pageParam.getPageNum(),pageParam.getPageSize());

        /**
         * 2.实现（多）排序
         * 根据排序参数实现排序功能，用遍历实现多条件排序（需要排序的参数可能不止一个：id、username...）
         * 按照排序参数数组中的索引顺序依次实现排序需求
         */
        for (int i = 0; i < pageParam.getOrderParams().length; i++) {
            // 调用pagehelper的排序方法对排序参数数组依次排序
            PageHelper.orderBy(pageParam.getOrderParams()[i]);
        }

        List<User> userList = userDao.getAllUser(pageParam.getModel());
        // 将userdao中查出来的userlist赋给userpageinfo并返回
        PageInfo<User> userPageInfo = new PageInfo<>(userList);

        return userPageInfo;
    }



    /**
     * @Cacheable(key = "#p0", value = "users")
     * 当缓存中没有想要的值（key）时，才调用下面的方法,
     * #p（取参数）0（下面方法中的第1个）及id  赋值给key
     * value 的值为下面方法返回值   todo(是不是对的)
     */
    @Cacheable(key = "#p0", value = "users")
    @Override
    public User getUserById(int id) {
        log.info("走的是数据库");
        return userDao.getUserById(id);
    }


    @CachePut(key = "#p0.id", value = "users")
    @Override
    public Object addUser(User user) {

        log.info("走的是数据库");

        user.setRoleId("general");
        userDao.addUser(user); // mapper文件中添加完后会执行selectKey并返回id的值

        return userDao.getUserById(user.getId()); // 根据id的值得到整个对象并返回
    }

    @CacheEvict(key = "#p0", value = "users")
    @Override
    public boolean deleteUserById(int id) {

        log.info("走的是数据库");

        return userDao.deleteUserById(id) == 1;
    }

    @Override
    public boolean updateUser(User user) {

//        log.info("走的是数据库");

        return userDao.updateUser(user) == 1;
    }

    @Override
    public User login(User user) {
//        PageParam<User> userPageParam = new PageParam<User>();
//        userPageParam.getModel().setUsername(username);
//        userPageParam.getModel().setPassword(password);
//        List<User> userList = (List<User>)getAllUser(userPageParam);
//        User user = null;
//        for (int i = 0; i < userList.size(); i++) {
//            if (userList.get(i) != null) {
//                user = userList.get(i);
//            }
//        }

        return userDao.login(user);
    }


}
