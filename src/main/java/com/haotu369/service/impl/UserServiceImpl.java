package com.haotu369.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.base.MessageResult;
import com.haotu369.mapper.UserMapper;
import com.haotu369.model.User;
import com.haotu369.service.UserService;
import com.haotu369.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/19
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageResult messageResult;

    @Override
    public JSONObject checkUsername(String username) {
        int count = userMapper.getUserByName(username);
        if (count >= 1) {
            return messageResult.message(-1, "用户名已存在");
        }
        return messageResult.message(1, "验证用户名成功");
    }

    @Override
    public JSONObject addUser(User user) {
        String raw= user.getPassword();
        String cipher = EncryptionUtil.encryption(raw);
        user.setPassword(cipher);

        userMapper.addUser(user);
        return messageResult.message(1, "注册成功");
    }
}
