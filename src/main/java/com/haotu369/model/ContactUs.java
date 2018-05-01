package com.haotu369.model;

import java.util.UUID;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/22
 */
public class ContactUs {
    private Integer id;

    private String name;

    private String email;

    private String subject;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
