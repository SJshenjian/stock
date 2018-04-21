package com.haotu369.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户相关操作
 *
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/2
 */
@Controller
public class UserAction {

    // 首页
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    // 注册
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    // 登录
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
