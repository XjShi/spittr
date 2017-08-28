package com.spittr.pojo;

import com.spittr.enums.AttachmentType;

public class File {
    private String name;
    private int type;

    public File() {
    }

    public File(String name, AttachmentType attachmentType) {
        this.name = name;
        this.type = attachmentType.getType();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
