package com.example.helloworld.windowService.window;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WindowInfo {
    private WindowStatus windowStatus;
    private String timestamp;
    private String username;
    private List<WindowsNodesInfos> windowsNodes = new ArrayList<>();

    public List<WindowsNodesInfos> getWindowsNodes() {
        return windowsNodes;
    }

    public void setWindowsNodes(List<WindowsNodesInfos> windowsNodes) {
        this.windowsNodes = windowsNodes;
    }

    public WindowStatus getWindowStatus() {
        return windowStatus;
    }

    public void setWindowStatus(WindowStatus windowStatus) {
        this.windowStatus = windowStatus;
    }

    public void setUser(String username) {
        this.username = username;
    }
    public String getUser() {
        return this.username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp() {
        this.timestamp = LocalDateTime.now().toString();
    }

}
