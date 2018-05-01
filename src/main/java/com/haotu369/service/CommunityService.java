package com.haotu369.service;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.model.ContactUs;
import com.haotu369.model.FAQ;
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

    public JSONObject saveContactUs(ContactUs contactUs);

    public List<FAQ> listFaq();
}
