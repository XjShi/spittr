package com.spittr.pojo;

import java.sql.Timestamp;
import java.util.List;

public class Comment {
    private long id;
    private String username;
    private long spittleId;
    private long commentedId;
    private String text;
    private Timestamp createTime;
    private boolean enabled;
    private List<Attachment> attachmentList;

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

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
