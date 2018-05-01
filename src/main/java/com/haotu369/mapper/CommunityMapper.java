package com.haotu369.mapper;

import com.haotu369.model.ContactUs;
import com.haotu369.model.FAQ;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
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
}
