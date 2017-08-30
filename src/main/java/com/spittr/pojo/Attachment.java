package com.spittr.pojo;

public class Attachment {
    private long id;
    private int type;
    private String content;
    private long ownerId;   //spittleId, commentId

    public Attachment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }
}
