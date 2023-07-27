package com.baizhi.service.impl;

import com.baizhi.entity.User;
import com.baizhi.mapper.UserMapper;
import com.baizhi.service.UserService;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author MiracloW
 */
@Service
@Transactional
public class UserServiceimpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) {
        //1.根据用户名查询数据库中是否存在改用户
        User userDB = userMapper.findByUserName(user.getUsername());
        //2.判断用户是否存在
        if(!ObjectUtils.isEmpty(userDB)) throw new RuntimeException("当前用户名已被注册!");
        //3.注册用户&明文的密码加密  特点: 相同字符串多次使用md5就行加密 加密结果始终是一致
        String newPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(newPassword);
        userMapper.save(user);
    }

    @Override//xiaochen  123 ====>     xiaochen   xxed
    public User login(String username, String password) {
        //1.根据用户名查询用户
        User user = userMapper.findByUserName(username);
        if(ObjectUtils.isEmpty(user)) throw new RuntimeException("用户名不正确!");
        //2.比较密码
        String passwordSecret = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if(!user.getPassword().equals(passwordSecret)) throw new RuntimeException("密码输入错误!");
        return user;
    }
}
