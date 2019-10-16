package com.yrp.service;

import com.yrp.po.User;

/**
 * @Auther: 南迪叶先生:https://www.cnblogs.com/ye888/
 * @Date: 2019/9/28
 * @Description: com.yrp.service
 * @version: 1.0
 */
public interface Userservice {

    /**
     * 用户登录检测
     * @param
     * @return
     */
    User checkUser(String username,String password);

}
