package com.core.app.data.user;

public enum UserType {

    NORMAL(1),

    SUPER(2);

    public final int code;

    UserType(int code) {
        this.code = code;
    }

    public static UserType from(int code) {
        for (UserType userType : values()) {
            if (userType.code == code) {
                return userType;
            }
        }
        return NORMAL;
    }
}