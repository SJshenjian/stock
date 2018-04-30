package com.haotu369.service.impl;

import com.haotu369.mapper.NewsMapper;
import com.haotu369.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/21
 */
@Service
public class NewsServiceImpl implements NewsService{

    @Autowired
    private NewsMapper newsDao;

    @Override
    public List listNews(String type) {
        if ("sina".equals(type)) {
            return newsDao.listSinaNews();
        }
        if ("immediate".equals(type)) {
            return newsDao.listImmediateNews();
        }
        return null;
    }
}
