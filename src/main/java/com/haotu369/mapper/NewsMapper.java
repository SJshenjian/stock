package com.haotu369.mapper;

import com.haotu369.model.ImmediateNews;
import com.haotu369.model.SinaNews;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/21
 */
@Repository
public interface NewsMapper {

    public List<SinaNews> listSinaNews();

    public List<ImmediateNews> listImmediateNews();
}
