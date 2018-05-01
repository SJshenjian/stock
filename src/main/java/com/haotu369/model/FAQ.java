package com.haotu369.model;

import java.sql.Timestamp;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/5/1
 */
public class FAQ {
    private Integer id;

    private String name;

    private String content;

    private Timestamp date;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
