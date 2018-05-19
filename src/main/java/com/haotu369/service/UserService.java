package com.haotu369.service;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.User;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/19
 */
public interface UserService {

    JSONObject checkUsername(String username);

    JSONObject addUser(User user);
}
