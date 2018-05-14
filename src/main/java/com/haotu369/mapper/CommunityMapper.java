package com.haotu369.mapper;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/22
 */
@Repository
public interface CommunityMapper {

    @Insert("INSERT INTO contact_us(id, name, email, subject, content) values(#{id}, #{name}, #{email}, #{subject}, #{content})")
    public void saveContactUs(ContactUs contactUs);

    @Select("SELECT * FROM faq")
    public List<FAQ> listFaq();

    @Insert("INSERT INTO article(name, content, tag) VALUES(#{name}, #{content}, #{tagId})")
    public void saveArticle(@Param("name") String name,@Param("content") String content, @Param("tagId") String tagId);

    @SelectProvider(type = com.haotu369.mapper.provider.ArticleProvider.class, method = "listArticle")
    @Results({
            @Result(column = "tag", property = "tag", one = @One(select = "getTag"))
    })
    public List<Article> listArticle(String type, int pageNo, int pageSize);

    @Select("SELECT * FROM tag")
    public List<Tag> listTag();

    @Select("SELECT * FROM tag WHERE id = #{id}")
    public Tag getTag(int id);

    @Select("UPDATE article SET like_count = like_count - 1 WHERE id = #{id};")
    public void removeLike(int id);

    @Select("UPDATE article SET like_count = like_count + 1 WHERE id = #{id};")
    public void updateLike(int id);

    @Select("SELECT COUNT(id) FROM article")
    public int getArticleCount();

    @Select("SELECT * FROM article WHERE id = #{id}")
    @Results({
            @Result(column = "tag", property = "tag", one = @One(select = "getTag"))
    })
    public Article getArticleDetail(int id);

    @Insert("INSERT INTO comment(content, article_id, user_id, article_name) VALUES(#{content}, #{articleId}, #{userId}, #{articleName})")
    public void addComment(Comment comment);

    @Select("SELECT * FROM comment WHERE article_id = #{id}")
    @Results({
            @Result(column = "user_id", property = "user", one = @One(select = "com.haotu369.mapper.UserMapper.getUser") )
    })
    public List<Comment> getComment(int id);


    @Select("SELECT * FROM comment ORDER BY date DESC limit #{pageNo}, #{pageSize}")
    @Results({
            @Result(column = "user_id", property = "user", one = @One(select = "com.haotu369.mapper.UserMapper.getUser") )
    })
    public List<Comment> listRecentComment(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);
}
