package com.haotu369.controller;

import com.haotu369.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 新闻事件
 *
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/5
 */
@Controller
@RequestMapping("/news")
public class NewsAction {

    @Autowired
    private NewsService newsService;

    @RequestMapping("/{type}")
    public String news(@PathVariable("type") String type, ModelMap modelMap) {
        List news = newsService.listNews(type);
        modelMap.put("news", news);
        return "news/" + type;
    }
}
