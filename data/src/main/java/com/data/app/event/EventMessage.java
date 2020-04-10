package com.data.app.event;

public class EventMessage {

    private int resultCode;
    private String resultValue;

    public EventMessage(int resultCode) {
        this.resultCode = resultCode;
    }

    public EventMessage(int resultCode, String resultValue) {
        this.resultCode = resultCode;
        this.resultValue = resultValue;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getResultValue() {
        return resultValue;
    }
}