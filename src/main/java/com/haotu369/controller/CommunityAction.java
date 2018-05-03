package com.haotu369.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.*;
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
    public String article(ModelMap modelMap) {
        List<Article> choiceArticle = communityService.listChoiceArticle(1, 4);
        int count = communityService.getArticleCount();
        modelMap.put("count", count);
        modelMap.put("choiceArticle", choiceArticle);
        return "community_article";
    }

    // 内容分页
    @RequestMapping("/articlePage")
    @ResponseBody
    public Object articlePage(int pageNo, int pageSize, ModelMap modelMap) {
        List<Article> article = communityService.listRecentArticle(pageNo, pageSize);
        return JSONObject.toJSON(article);
    }

    // 点赞
    @RequestMapping("/updateLike")
    @ResponseBody
    public JSONObject updateLike(int id) {
        return communityService.updateLike(id);
    }

    // 取消点赞
    @RequestMapping("/removeLike")
    @ResponseBody
    public JSONObject removeLike(int id) {
        return communityService.removeLike(id);
    }

    // 内容详情
    @RequestMapping("/articleDetail")
    public String articleDetail(int id, ModelMap modelMap) {
        Article article = communityService.getArticleDetail(id);
        List<Article> choiceArticle = communityService.listChoiceArticle(1, 4);
        modelMap.put("article", article);
        modelMap.put("choiceArticle", choiceArticle);
        return "community_article_detail";
    }

    // 文章内容评论
    @RequestMapping("/addComment")
    @ResponseBody
    public JSONObject addComment(Comment comment) {
        // TODO 从token中或许用户的id,判断用户是否为游客
        String userId = null;
        return communityService.addComment(comment, userId);
    }

    // 获取文章评论
    @RequestMapping("/listComment")
    @ResponseBody
    public Object listComment(int id) {
        List<Comment> comments = communityService.getComment(id);
        return JSONObject.toJSON(comments);
    }

    // 获取最新评论
    @RequestMapping("/listRecentComment")
    @ResponseBody
    public Object listRecentComment(int pageNo, int pageSize) {
        List<Comment> recentComments = communityService.listRecentComment(pageNo, pageSize);
        return JSONObject.toJSON(recentComments);
    }
}
