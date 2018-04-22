package com.haotu369.dao.impl;

import com.haotu369.dao.NewsDao;
import com.haotu369.model.ImmediateNews;
import com.haotu369.model.SinaNews;
import org.springframework.beans.factory.annotation.Autowired;
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
public class NewsDaoImpl implements NewsDao{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<SinaNews> listSinaNews() {
        Query query = new Query();
        query = query.with(new Sort(Sort.Direction.DESC, "releaseTime")).limit(6);
        return mongoTemplate.find(query, SinaNews.class);
    }

    @Override
    public List<ImmediateNews> listImmediateNews() {
        Query query = new Query();
        query = query.with(new Sort(Sort.Direction.DESC, "releaseTime")).limit(6);
        return mongoTemplate.find(query, ImmediateNews.class);
    }
}
