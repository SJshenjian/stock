package com.haotu369.service;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.News;

import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/21
 */
public interface NewsService {

    public List<News> listNews();
}
