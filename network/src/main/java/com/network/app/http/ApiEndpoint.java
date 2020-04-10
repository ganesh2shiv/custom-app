package com.network.app.http;

public enum ApiEndpoint {

    MOCK("https://www.apiary-mock.com"),

    RELEASE("https://api.custom.com"),

    CUSTOM();

    public String url;

    ApiEndpoint() {
    }

    ApiEndpoint(String url) {
        this.url = url;
    }

    public static ApiEndpoint from(String endpoint) {
        for (ApiEndpoint value : values()) {
            if (value.url != null && value.url.equals(endpoint)) {
                return value;
            }
        }
        return CUSTOM;
    }

    public static boolean isMockMode(String endpoint) {
        return from(endpoint) == MOCK;
    }
}