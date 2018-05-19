package com.haotu369.mapper;

import com.haotu369.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/3
 */
@Repository
public interface UserMapper {

    @Select("Select * FROM user WHERE id = #{id}")
    User getUser(int id);

    @Select("SELECT COUNT(id) FROM user WHERE username = #{username}")
    int getUserByName(String username);

    @Insert("INSERT INTO user(username, password) VALUES(#{username}, #{password})")
    void addUser(User user);
}
