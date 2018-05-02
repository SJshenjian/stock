package com.haotu369.mapper;

import com.haotu369.model.Article;
import com.haotu369.model.ContactUs;
import com.haotu369.model.FAQ;
import com.haotu369.model.Tag;
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
}
