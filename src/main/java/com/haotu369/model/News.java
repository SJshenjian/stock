package com.haotu369.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 字段命名与tushare-新浪股吧接口返回字段一致
 *
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/21
 */
@Document(collection="news.sina")
public class News {

    @Field("_id")
    private String id;

    @Field("title")
    private String title; // 标题

    @Field("content")
    private String content; // 内容

    @Field("ptime")
    private String releaseTime; // 发布日期

    @Field("rcounts")
    private String readCounts; // 阅读次数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getReadCounts() {
        return readCounts;
    }

    public void setReadCounts(String readCounts) {
        this.readCounts = readCounts;
    }
}
