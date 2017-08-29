package com.spittr.pojo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Spittle implements Serializable {
    private static final long serialVersionUID = 1L;    //serialVersionUID
    private Long id;
    private String username;
    private String text;
    private int attachType;
    private String attachContent;
    private String repost;  //  json text composed by reposter's username
    private String like;    //  idem
    private boolean enabled;
    private Timestamp publishTime;
    private List<Comment> commentList;

    public Spittle() {
    }

    public Spittle(String username, String text, int attachType, String attachContent, boolean enabled) {
        this.username = username;
        this.text = text;
        this.attachType = attachType;
        this.attachContent = attachContent;
        this.enabled = enabled;
    }

    public Spittle(String username, String text, boolean enabled) {
        this(username, text, 0, null, enabled);
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

    public int getAttachType() {
        return attachType;
    }

    public void setAttachType(int attachType) {
        this.attachType = attachType;
    }

    public String getAttachContent() {
        return attachContent;
    }

    public void setAttachContent(String attachContent) {
        this.attachContent = attachContent;
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
}
