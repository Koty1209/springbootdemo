package com.wenli.springbootdemo.common;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.wenli.springbootdemo.dao.UserDao;
import com.wenli.springbootdemo.model.User;

import java.util.List;

@Component
public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserDao userDao;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    // 用于授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = JwtUtil.getUsername(principalCollection.toString());
        User uCondition=new User();
        uCondition.setUsername(username);
        List<User> userList= userDao.getAllUser(uCondition);
        User user=userList.get(0);
        SimpleAuthorizationInfo simpleAuthorizationInfo =
                new SimpleAuthorizationInfo();
        if(user!=null){
            simpleAuthorizationInfo.addRole(user.getRoleId());
        }


//        Set<String> roleSet= new HashSet<String>();
//        roleSet.add(user.getRoleId());
//        simpleAuthorizationInfo.setStringPermissions(roleSet);

        return simpleAuthorizationInfo;


    }


    // 用于认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();

        String username = JwtUtil.getUsername(token);

        if (username == null) {
//            throw new MyException(HttpCode.ERROR).msg("token 无效");
            throw new AuthenticationException("token 无效");
        }

        User uCondition=new User();
        uCondition.setUsername(username);
        List<User> userList= userDao.getAllUser(uCondition);

        User user = userList.get(0);

        if (user == null) {
//            throw new MyException(HttpCode.ERROR).msg("用户不存在");
            throw new AuthenticationException("用户不存在");
        }

        if (!JwtUtil.verify(token, username, user.getPassword())) {
//            throw new MyException(HttpCode.ERROR).msg("用户名和密码错误");
            throw new AuthenticationException("用户名和密码错误");
        }

        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
