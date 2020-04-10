package com.user.app.data;

public enum UserType {

    NORMAL(1),

    SUPER(2);

    public final int role;

    UserType(int role) {
        this.role = role;
    }

    public static UserType from(int role) {
        for (UserType userType : values()) {
            if (userType.role == role) {
                return userType;
            }
        }
        return NORMAL;
    }
}