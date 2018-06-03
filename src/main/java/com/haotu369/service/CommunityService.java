package com.haotu369.service;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.*;
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

     JSONObject saveContactUs(ContactUs contactUs);

     List<FAQ> listFaq();

     JSONObject saveArticle(Article article, String tagId);

     List<Article> listRecentArticle(int pageNo, int pageSize);

     List<Article> listChoiceArticle(int pageNo, int pageSize);

     List<Article> listArticleByTag(int tagId, int pageNo, int pageSize);

     int getArticleCount(String tagId);

     List<Tag> listTag();

     JSONObject updateLike(int id);

     JSONObject removeLike(int id);

     Article getArticleDetail(int id);

     JSONObject addComment(Comment comment, Integer userId);

     List<Comment> getComment(int id);

     List<Comment> listRecentComment(int pageNo, int pageSize);

     List<Article> search(String content);
}
