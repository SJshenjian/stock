package com.haotu369.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : shenjian
 * @PackageName : com.haotu369.controller
 * @Created : 2018/4/11
 * @Version : V1.0
 * @Des : 选股社区
 */
@Controller
public class Community {

    @RequestMapping("/community")
    public String community() {
        return "community";
    }
}
