package com.network.app.oauth;

public class AccountConstants {

    public static final long TOKEN_EXPIRE_IN_MILLIS = 60 * 60 * 1000;

    public static final String TYPE_READ_ONLY = "Read only";
    public static final String TYPE_READ_ONLY_LABEL = "Read only access to the account";

    public static final String TYPE_FULL_ACCESS = "Full access";
    public static final String TYPE_FULL_ACCESS_LABEL = "Full access to the account";

    public final static String KEY_TOKEN_TYPE = "tokenType";
    public final static String KEY_REFRESH_TOKEN = "refreshToken";
    public final static String KEY_TOKEN_EXPIRE_IN = "expireIn";
}