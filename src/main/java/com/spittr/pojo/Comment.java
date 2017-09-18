package com.spittr.pojo;

import java.sql.Timestamp;

public class Comment {
    private long id;
    private String username;
    private long spittleId;
    private long commentedId;
    private String text;
    private Timestamp createTime;
    private boolean enabled = true;
    private String attachment;

    public Comment() {
    }

    public Comment(String username, long spittleId, String text, String attachment) {
        this.username = username;
        this.spittleId = spittleId;
        this.text = text;
        this.attachment = attachment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getSpittleId() {
        return spittleId;
    }

    public void setSpittleId(long spittleId) {
        this.spittleId = spittleId;
    }

    public long getCommentedId() {
        return commentedId;
    }

    public void setCommentedId(long commentedId) {
        this.commentedId = commentedId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", spittleId=" + spittleId +
                ", commentedId=" + commentedId +
                ", text='" + text + '\'' +
                ", createTime=" + createTime +
                ", enabled=" + enabled +
                ", attachment=" + attachment +
                '}';
    }
}
