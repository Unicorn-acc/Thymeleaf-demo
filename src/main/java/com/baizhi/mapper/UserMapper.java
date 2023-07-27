package com.baizhi.mapper;

import com.baizhi.entity.User;

/**
 * @author MiracloW
 */
public interface UserMapper {

    //根据用户查询用户
    User findByUserName(String username);

    //保存用户信息
    void save(User user);

}
