package com.yrp.dao;

import com.yrp.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: 南迪叶先生:https://www.cnblogs.com/ye888/
 * @Date: 2019/9/28
 * @Description: com.yrp.dao
 * @version: 1.0
 */
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * 通过用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username, String password);
}
