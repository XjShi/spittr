package com.spittr.pojo;

import com.spittr.enums.AttachmentType;

public class File {
    private String name;
    private AttachmentType type;

    public File() {
    }

    public File(String name, AttachmentType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }
}
