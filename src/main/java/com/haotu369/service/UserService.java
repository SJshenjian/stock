package com.haotu369.service;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/19
 */
public interface UserService {

    JSONObject checkUsername(String username);

    JSONObject addUser(User user);

    JSONObject checkLogin(User user, HttpServletResponse response);

    List<User> getUserByName(String username);

    JSONObject logout();
}
