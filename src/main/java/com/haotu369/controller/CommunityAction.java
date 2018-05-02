package com.haotu369.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.Article;
import com.haotu369.model.ContactUs;
import com.haotu369.model.FAQ;
import com.haotu369.model.Tag;
import com.haotu369.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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
    public String community(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        // 精选内容
        List<Article> choiceArticle = communityService.listChoiceArticle(1, 6);
        // 最新内容
        List<Article> recentArticle = communityService.listRecentArticle(1, 6);
        // 分类标签
        List<Tag> tag = communityService.listTag();

        modelMap.put("choiceArticle", choiceArticle);
        modelMap.put("recentArticle", recentArticle);
        modelMap.put("tag", tag);
        return "community_index";
    }

    // 联系我们
    @RequestMapping("/contact")
    public String contact(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        List<Article> recentArticle = communityService.listRecentArticle(1, 4);
        modelMap.put("recentArticle", recentArticle);
        return "community_contact";
    }

    // 保存联系我们消息内容
    @RequestMapping("/saveContact")
    @ResponseBody
    public JSONObject saveContact(ContactUs contactUs) {
        return communityService.saveContactUs(contactUs);
    }

    // 常见问题
    @RequestMapping("/faq")
    public String listFaq(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        List<Article> recentArticle = communityService.listRecentArticle(1, 4);
        List<Tag> tag = communityService.listTag();
        List<FAQ> faq = communityService.listFaq();

        modelMap.put("recentArticle", recentArticle);
        modelMap.put("tag", tag);
        modelMap.put("faq", faq);
        return "community_faq";
    }

    // 内容列表
    @RequestMapping("/article")
    public String listArticle(String pageNo, String pageSize, ModelMap modelMap) {
        pageNo = (pageNo != null ? pageNo : "1");
        pageSize = (pageSize != null ? pageSize : "5");
        List<Article> article = communityService.listRecentArticle(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        List<Article> choiceArticle = communityService.listChoiceArticle(1, 4);
        modelMap.put("article", article);
        modelMap.put("choiceArticle", choiceArticle);
        return "community_article";
    }
}
