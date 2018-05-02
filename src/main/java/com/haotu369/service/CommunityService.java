package com.haotu369.service;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.Article;
import com.haotu369.model.ContactUs;
import com.haotu369.model.FAQ;
import com.haotu369.model.Tag;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/22
 */
public interface CommunityService {

    public JSONObject saveContactUs(ContactUs contactUs);

    public List<FAQ> listFaq();

    public List<Article> listRecentArticle(int pageNo, int pageSize);

    public List<Article> listChoiceArticle(int pageNo, int pageSize);

    public int getArticleCount();

    public List<Tag> listTag();

    public JSONObject updateLike(int id);

    public JSONObject removeLike(int id);
}
