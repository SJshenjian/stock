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

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Override
    public JSONObject saveContactUs(ContactUs contactUs) {
        communityMapper.saveContactUs(contactUs);
        sendSimpleEmail("haotu369@sina.com", contactUs, host, username, password);
        return message(1, "已收到您的消息，敬请等待工作人员回复");
    }

    private void sendSimpleEmail(String to, ContactUs contactUs, String host, String username, String password) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(contactUs.getSubject());
        simpleMailMessage.setText("发送者:" + contactUs.getName() + "\n邮箱号:" + contactUs.getEmail() + "\n文本内容:" + contactUs.getContent());

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.send(simpleMailMessage);
    }

    private JSONObject message(int code, String note) {
        JSONObject result = new JSONObject();
        result.put("O_CODE", code);
        result.put("O_NOTE", note);
        return result;
    }
}
