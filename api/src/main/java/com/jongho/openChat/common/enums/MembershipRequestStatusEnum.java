package com.jongho.openChat.common.enums;

public enum MembershipRequestStatusEnum {
    PARTICIPATION_REQUEST(1),
    APPROVED(2),
    REJECTED(3);

    private final int value;

    MembershipRequestStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
