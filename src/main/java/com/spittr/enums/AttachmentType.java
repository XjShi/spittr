package com.spittr.enums;

public enum AttachmentType {
    NONE(0),
    IMAGE(1),
    AUDIO(2),
    VIDEO(3);

    private int type;

    AttachmentType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
