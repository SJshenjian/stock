package com.haotu369.controller;

import com.haotu369.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 新闻事件
 *
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/5
 */
@Controller
public class NewsAction {

    @Autowired
    private NewsService newsService;

    @RequestMapping("/news/{type}")
    public String news(@PathVariable("type") String type) {
        newsService.listNews();
        return "news_" + type;
    }
}
