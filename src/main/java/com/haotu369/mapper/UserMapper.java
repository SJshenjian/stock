package com.haotu369.mapper;

import com.haotu369.model.User;
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
    public User getUser(int id);
}
