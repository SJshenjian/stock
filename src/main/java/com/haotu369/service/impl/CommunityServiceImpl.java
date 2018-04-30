package com.haotu369.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.haotu369.mapper.CommunityMapper;
import com.haotu369.model.ContactUs;
import com.haotu369.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/22
 */
@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public JSONObject saveContactUs(ContactUs contactUs) {
        communityMapper.saveContactUs(contactUs);
        sendSimpleEmail("SJshenjian@outlook.com", contactUs.getSubject(), contactUs.getContent());
        return message(1, "已收到您的消息，敬请等待工作人员回复");
    }

    private void sendSimpleEmail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);

        JavaMailSender javaMailSender = new JavaMailSenderImpl();
        javaMailSender.send(simpleMailMessage);
    }

    private JSONObject message(int code, String note) {
        JSONObject result = new JSONObject();
        result.put("O_CODE", code);
        result.put("O_NOTE", note);
        return result;
    }
}
