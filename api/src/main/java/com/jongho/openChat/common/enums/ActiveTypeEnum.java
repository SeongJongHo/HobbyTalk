package com.jongho.openChat.common.enums;

public enum ActiveTypeEnum {
    ACTIVE(1),
    INACTIVE(0);

    private final int type;

    ActiveTypeEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
