package com.haotu369.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author : Jian Shen
 * @version : V1.0
 * @date : 2018/4/22
 */
@Document(collection="news.immediate")
public class ImmediateNews {

    @Field("_id")
    private String id;

    @Field("classify")
    private String classify;

    @Field("title")
    private String title;

    @Field("time")
    private String releaseTime;

    @Field("url")
    private String url;

    @Field("content")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
