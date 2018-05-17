package com.haotu369.mapper.provider;

import com.alibaba.druid.util.StringUtils;
import com.haotu369.model.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/2
 */
public class ArticleProvider {

    public String listArticle(String type, int pageNo, int pageSize) {
        int begin = (pageNo - 1) * pageSize;
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM article ");
        if ("choice".equalsIgnoreCase(type)) {
            builder.append("ORDER BY like_count DESC ");
        } else if("recent".equalsIgnoreCase(type)) {
            builder.append("ORDER BY date DESC ");
        } else {
            builder.append("ORDER BY date DESC ");
        }
        builder.append(" limit " + begin + ", " + pageSize);
        return builder.toString();
    }

    public String listArticleByTag(int tagId, int pageNo, int pageSize) {
        int begin = (pageNo - 1) * pageSize;
        String sql = "SELECT * FROM article WHERE tag = " + tagId + " limit " + begin + ", " + pageSize;
        return sql;
    }

    public String countArticle(String tagId) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(id) FROM article");

        if (!StringUtils.isEmpty(tagId)) {
            builder.append(" WHERE tag = ").append(tagId);
        }

        return builder.toString();
    }

    public String search(@Param("params") String[] params) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < params.length; i++) {
            builder.append("SELECT * FROM article WHERE POSITION( '")
                    .append(params[i])
                    .append("' IN name)");
            if (i != params.length -1) {
                builder.append(" UNION ");
            }
        }
        return builder.toString();
    }

}
