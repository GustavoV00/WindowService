package com.example.helloworld.windowService;

import android.view.accessibility.AccessibilityNodeInfo;


import com.example.helloworld.windowService.network.CustomRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Windows {

    private Map<String, String> lastWindowsNodes = new HashMap<>();
    private Utils utils;


    public Windows() {
        this.utils = new Utils();
    }

    public void compareLatestWindowsEvents(Map<String, String> windowNodes) {
        if (!windowNodes.equals(lastWindowsNodes)) {
            lastWindowsNodes.clear();
            lastWindowsNodes.putAll(windowNodes);
            utils.printWindowData(lastWindowsNodes);
        }
    }

    public void enumerateWindows(AccessibilityNodeInfo node, Map<String, String> windowNodes) {
        if (windowNodes.containsKey(node) || node == null) {
            return;
        }

        addWindowsNode(node, windowNodes);

        int childCount = node.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo childNode = node.getChild(i);
            enumerateWindows(childNode, windowNodes);
            childNode.recycle(); // recycle each child node
        }
    }

    public void addWindowsNode(AccessibilityNodeInfo node, Map<String, String> windowNodes) {

        if (node.getClassName().equals("android.widget.FrameLayout")) {
            String windowTitle = node.getContentDescription() != null && !node.getContentDescription().toString().isEmpty() ? node.getContentDescription().toString() : "";
            String packageName = node.getPackageName() != null ? node.getPackageName().toString() : "Package not available";

            if (windowTitle.equals("")) {
                String[] result = packageName.split("\\.");
                windowTitle = result[result.length - 1];
            }
            windowTitle = windowTitle.substring(0, 1).toUpperCase() + windowTitle.substring(1);

            windowNodes.put(windowTitle, packageName);
        }
    }

    public void sendWindowsToServer(List<String> buffer) {

    }

}
