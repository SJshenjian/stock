package com.haotu369.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.base.Result;
import com.haotu369.mapper.UserMapper;
import com.haotu369.model.User;
import com.haotu369.service.UserService;
import com.haotu369.util.CookieUtils;
import com.haotu369.util.EncryptionUtils;
import com.haotu369.util.RequestUtils;
import com.haotu369.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/19
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public JSONObject checkUsername(String username) {
        List<User> users = userMapper.getUserByName(username);
        if (users.size() >= 1) {
            return Result.message(-1, "用户名已存在");
        }
        return Result.message(1, "验证用户名成功");
    }

    @Override
    public JSONObject addUser(User user) {
        String raw= user.getPassword();
        String cipher = EncryptionUtils.encryption(raw);
        user.setPassword(cipher);

        userMapper.addUser(user);
        return Result.message(1, "注册成功");
    }

    @Override
    public JSONObject checkLogin(User user, HttpServletResponse response) {
        List<User> users = userMapper.getUserByName(user.getUsername());
        if (users.size() <= 0) {
            return Result.message(-1, "用户名不正确");
        }
        User cipherUser = users.get(0);
        boolean result = EncryptionUtils.equals(user.getPassword(), cipherUser.getPassword());
        if (!result) {
            return Result.message(-1, "密码不正确");
        }

        String uuid = UUID.randomUUID().toString();
        String token = TokenUtils.buildToken(uuid);
        String userToken = TokenUtils.buildToken(JSONObject.toJSON(cipherUser).toString()); // 对用户信息处理后存储
        int expiry = 1800;

        redisTemplate.opsForValue().set(token, userToken, expiry, TimeUnit.SECONDS);

        CookieUtils.saveCookie(response,"auth-token", token, 24 * 60 * 60);

        JSONObject cookieUser = new JSONObject();
        cookieUser.put("username", cipherUser.getUsername());
        cookieUser.put("img", cipherUser.getImg());

        try {
            // URLEncoder.encode 防止Cookie存储Json异常
            CookieUtils.saveCookie(response, "userInfo", URLEncoder.encode(cookieUser.toJSONString(), "utf-8"), 24 * 60 * 60);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return Result.message(1, "登录成功");
    }

    @Override
    public List<User> getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public JSONObject logout() {
        String token = getTokenFromCookie();

        redisTemplate.opsForValue().set(token, "", 2, TimeUnit.SECONDS);
        CookieUtils.removeAllCookies();

        return Result.message(1, "退出成功");
    }

    @Override
    public Integer getUserIdFromCookie() {
        String token = getTokenFromCookie();
        if (token != null) {
            String userToken = (String) redisTemplate.opsForValue().get(token);
            Integer userId = (Integer) TokenUtils.parseToken(userToken).get("id");
            return userId;
        }
        return 0; // 表示游客
    }

    private String getTokenFromCookie() {
        Cookie cookie = CookieUtils.getCookieByName(RequestUtils.getRequest(), "auth-token");
        String token = null;
        if (cookie != null) {
            token = cookie.getValue();
        }
        return token;
    }
}
