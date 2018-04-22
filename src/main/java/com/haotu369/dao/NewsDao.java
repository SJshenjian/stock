package com.haotu369.dao;

import com.haotu369.model.ImmediateNews;
import com.haotu369.model.SinaNews;

import java.util.List;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/21
 */
public interface NewsDao{

    public List<SinaNews> listSinaNews();

    public List<ImmediateNews> listImmediateNews();
}
