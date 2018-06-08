package com.spittr.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.owasp.esapi.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Spittle implements Serializable {
    private static final long serialVersionUID = 1L;    //serialVersionUID
    private Long id;
//    private String username;
    private String text;
    private String attachment;
    private boolean enabled;
    private Timestamp publishTime;
    private List<Comment> commentList;
    private Spitter user;

    public Spittle() {
    }

    // todo: remove username parameter
    public Spittle(String username, String text, boolean enabled) {
//        this.username = username;
        this.text = text;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public Spitter getUser() {
        return user;
    }
    public void setUser(Spitter user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Spittle{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", enabled=" + enabled +
                ", publishTime=" + publishTime +
                ", commentList=" + commentList +
                ", attachment=" + attachment +
                '}';
    }
}
