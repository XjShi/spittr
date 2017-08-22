package com.spittr.pojo;

import java.sql.Timestamp;

public class Comment {
    private long id;
    private String username;
    private long spittleId;
    private long commentedId;
    private String text;
    private int attachType;
    private int attachContent;
    private Timestamp createTime;
    private boolean enabled;

    public Comment() {
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

    public int getAttachType() {
        return attachType;
    }

    public void setAttachType(int attachType) {
        this.attachType = attachType;
    }

    public int getAttachContent() {
        return attachContent;
    }

    public void setAttachContent(int attachContent) {
        this.attachContent = attachContent;
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
}
