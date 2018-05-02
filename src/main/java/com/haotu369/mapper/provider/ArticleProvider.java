package com.haotu369.mapper.provider;

import com.haotu369.model.Article;

import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/2
 */
public class ArticleProvider {

    public String listArticle(String type, int pageNo, int pageSize) {
        int begin = (pageNo - 1) * pageSize;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM article ");
        if ("choice".equalsIgnoreCase(type)) {
            sb.append("ORDER BY like_count DESC ");
        } else if("recent".equalsIgnoreCase(type)) {
            sb.append("ORDER BY date DESC ");
        } else {
            sb.append("ORDER BY date DESC ");
        }
        sb.append(" limit " + begin + ", " + pageSize);
        return sb.toString();
    }
}
