package com.jongho.openChat.common.enums;

public enum ConnectionInfoFieldEnum {
    ACTIVE("active"),
    UN_READ_MESSAGE_COUNT("unReadMessageCount"),
    LAST_EXIT_TIME("lastExitTime");
    private final String field;

    ConnectionInfoFieldEnum(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
