package com.haotu369.mapper.impl;

import com.haotu369.mapper.NewsMapper;
import com.haotu369.model.ImmediateNews;
import com.haotu369.model.SinaNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/21
 */
@Repository
@Primary // 带有具体实现的加该注解，防止mybatis报错
public class NewsMapperImpl implements NewsMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<SinaNews> listSinaNews() {
        Query query = new Query();
        query = query.with(new Sort(Sort.Direction.DESC, "id")).limit(6);
        return mongoTemplate.find(query, SinaNews.class);
    }

    @Override
    public List<ImmediateNews> listImmediateNews() {
        Query query = new Query();
        query = query.with(new Sort(Sort.Direction.DESC, "id")).limit(6);
        return mongoTemplate.find(query, ImmediateNews.class);
    }
}
