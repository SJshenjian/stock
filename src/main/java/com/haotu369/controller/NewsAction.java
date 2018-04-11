package com.haotu369.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : shenjian
 * @PackageName : com.haotu369.controller
 * @Created : 2018/4/5
 * @Version : V1.0
 * @Des :
 */
@Controller
public class NewsAction {

    @RequestMapping("/news/{type}")
    public String news(@PathVariable("type") String type) {
        return "news_" + type;
    }
}
