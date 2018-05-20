package com.haotu369.controller;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.User;
import com.haotu369.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户相关操作
 *
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/2
 */
@Controller
public class UserAction {

    @Autowired
    private UserService userService;

    // 首页
    @RequestMapping("/index")
    public String index() {
        return "stock/index";
    }

    // 添加用户
    @RequestMapping("/addUser")
    @ResponseBody
    public JSONObject addUser(User user) {
        return userService.addUser(user);
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

    // 登录判断
    @RequestMapping("/checkLogin")
    public String checkLogin(User user, ModelMap modelMap, HttpServletResponse response) {
        JSONObject result = userService.checkLogin(user, response);
        if ((int)result.get("O_CODE") == 1) {
            return "redirect:/index";
        }
        modelMap.put("error", result.get("O_NOTE"));
        return "login";
    }


    // 检查用户名是否存在
    @RequestMapping("/checkUsername")
    @ResponseBody
    public JSONObject checkUsername(String username) {
        return userService.checkUsername(username);
    }

    // 用户退出
    @RequestMapping("/logout")
    @ResponseBody
    public JSONObject logout() {
        return userService.logout();
    }
}
