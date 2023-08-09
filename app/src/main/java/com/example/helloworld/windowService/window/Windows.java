package com.example.helloworld.windowService.window;

import static com.example.helloworld.windowService.network.CustomRequestFactory.createPostRequest;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;


import com.example.helloworld.windowService.Utils;
import com.example.helloworld.windowService.network.CustomRequest;
import com.example.helloworld.windowService.network.RequestActions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import okhttp3.Request;

public class Windows {

    private List<WindowsNodesInfos> lastWindowsNodes = new ArrayList<>();
    private Utils utils;


    public Windows() {
        this.utils = new Utils();
    }

    public boolean compareLatestWindowsEvents(List<WindowsNodesInfos> windowNodes) {
//        System.out.println(windowNodes);
        if (!windowNodes.equals(lastWindowsNodes)) {
            lastWindowsNodes.clear();
            lastWindowsNodes.addAll(windowNodes);
            return true;

        }
        return false;
    }

    public void enumerateWindows(AccessibilityNodeInfo node, List<WindowsNodesInfos> windowNodes) {
        if (node == null) {
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

    public void addWindowsNode(AccessibilityNodeInfo node, List<WindowsNodesInfos> windowNodes) {

        if (node.getClassName().equals("android.widget.FrameLayout")) {
            String windowTitle = node.getContentDescription() != null && !node.getContentDescription().toString().isEmpty() ? node.getContentDescription().toString() : "";
            String packageName = node.getPackageName() != null ? node.getPackageName().toString() : "Package not available";


            boolean hasTitle = true;
            if (windowTitle.equals("")) {
                String[] result = packageName.split("\\.");
                windowTitle = result[result.length - 1];
                hasTitle = false;
            }
            windowTitle = windowTitle.substring(0, 1).toUpperCase() + windowTitle.substring(1);

            WindowTitleInfo windowTitleInfo = new WindowTitleInfo(windowTitle, hasTitle);
            WindowsNodesInfos windowsNodesInfos = new WindowsNodesInfos(windowTitleInfo, packageName);
            if(!windowNodes.contains(windowsNodesInfos))
                windowNodes.add(windowsNodesInfos);
        }
    }

    public void sendWindowsToServer(String buffer) {
        RequestActions requestActions = new RequestActions();
//        System.out.println(buffer);
//        CustomRequest postRequest = createPostRequest("http://172.29.117.71:8080/windows", buffer);
        CustomRequest postRequest = createPostRequest("http://18.189.157.240/windows ", buffer);
        Request request = postRequest.buildRequest();
        CompletableFuture<String> future = requestActions.sendRequest(request, postRequest);

        future.thenAccept(response -> {
            Log.d("Response", "Success: " + response);
        }).exceptionally(ex -> {
//            ex.printStackTrace();
            Log.d("Response", "Failure2: " + ex);
            return null;
        });
    }

    public WindowStatus verifyFocusedWindows(List<WindowsNodesInfos> nodes) {
        if (nodes.size() > 1)
            return WindowStatus.SWITCH;
        return WindowStatus.ACTIVE;
    }

}
