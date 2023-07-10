package com.example.helloworld.windowService.window;

public enum WindowStatus {
    ACTIVE("ACTIVE"),
    SWITCH("SWITCH");

    private final String status;

    WindowStatus(String status) {
        this.status = status;
    }

    public String getWindowStatus() {
        return this.status;
    }
}
