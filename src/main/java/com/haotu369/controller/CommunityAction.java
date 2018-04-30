package com.haotu369.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.ContactUs;
import com.haotu369.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Properties;

/**
 * 选股社区
 *
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/11
 */
@Controller
@RequestMapping("/community")
public class CommunityAction {

    @Autowired
    private CommunityService communityService;

    // 社区首页
    @RequestMapping("/index")
    public String community() {
        return "community_index";
    }

    // 联系我们
    @RequestMapping("/contact")
    public String contact() {
        return "community_contact";
    }

    // 保存联系我们消息内容
    @RequestMapping("/saveContact")
    @ResponseBody
    public JSONObject saveContact(ContactUs contactUs) {
        return communityService.saveContactUs(contactUs);
    }

    // 常见问题
    @RequestMapping("/faqs")
    public String faqs() {
        return "community_faqs";
    }

    // 内容列表
    @RequestMapping("/articles")
    public String articles() {
        return "community_articles";
    }
}
