package com.spittr.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Spittle implements Serializable {
    private static final long serialVersionUID = 1L;    //serialVersionUID
    private Long id;
    private String username;
    private String text;
    private String repost;  //  json text composed by reposter's username
    private String like;    //  idem
    private boolean enabled;
    private Timestamp publishTime;
    private List<Comment> commentList;
    private List<Attachment> attachmentList;

    public Spittle() {
    }

    public Spittle(String username, String text, boolean enabled) {
        this.username = username;
        this.text = text;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getRepost() {
        return repost;
    }

    public void setRepost(String repost) {
        this.repost = repost;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
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

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    @Override
    public String toString() {
        return "Spittle{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", text='" + text + '\'' +
                ", repost='" + repost + '\'' +
                ", like='" + like + '\'' +
                ", enabled=" + enabled +
                ", publishTime=" + publishTime +
                ", commentList=" + commentList +
                ", attachmentList=" + attachmentList +
                '}';
    }
}
