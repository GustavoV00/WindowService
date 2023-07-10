package com.example.helloworld.windowService.window;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WindowInfo {
    private WindowStatus windowStatus;
    private String timestamp;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp() {
        this.timestamp = LocalDateTime.now().toString();
    }

}
