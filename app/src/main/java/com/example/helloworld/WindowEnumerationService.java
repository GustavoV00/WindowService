package com.example.helloworld;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.helloworld.windowService.Permissions;
import com.example.helloworld.windowService.Utils;
import com.example.helloworld.windowService.window.WindowInfo;
import com.example.helloworld.windowService.window.Windows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WindowEnumerationService extends AccessibilityService {

    private Windows windows;
    private Activity activity;
    private Context context;
    private Permissions permissions;
    private Utils utils;
    private Integer bufferIndex;
    private long duration;
    private long startTime;
    private List<WindowInfo> buffer = new ArrayList<>();
    private String jsonBuffer;

    public WindowEnumerationService() {

    }

    public WindowEnumerationService(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (this.permissions.hasCorrectPermissions()) {
            switch (eventType) {
                case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                    WindowInfo windowInfo = new WindowInfo();
                    if (rootNode != null) {
                        this.windows.enumerateWindows(rootNode, windowInfo.getWindowsNodes());
                        windowInfo.setWindowStatus(this.windows.verifyFocusedWindows(windowInfo.getWindowsNodes()));
                        windowInfo.setTimestamp();

                        if (this.utils.compareWindowsInBuffer(buffer, windowInfo)) {
                            buffer.add(windowInfo);
                            Log.d("Buffer size: ", "" + buffer.size());
                            if (this.utils.timeCheckUp(startTime, duration) || utils.bufferIsFull(buffer)) {
                                jsonBuffer = this.utils.convertAllBufferToJson(buffer, jsonBuffer);
                                Log.d("Buffer test: ", jsonBuffer);
                                this.startTime = System.currentTimeMillis();
                                this.windows.sendWindowsToServer(jsonBuffer);
                                buffer.clear();
                                jsonBuffer = "";
                            }
                        }
//                        windowInfo.getWindowsNodes().clear();
                        rootNode.recycle(); // recycle root node
                    }
                    break;
            }

        } else {
            this.permissions.requestCorrectPermissions();
        }

    }


    @Override
    public void onInterrupt() {
        buffer.removeIf(element -> element.getWindowsNodes().size() == 0);
        jsonBuffer = this.utils.convertAllBufferToJson(buffer, jsonBuffer);
        this.windows.sendWindowsToServer(jsonBuffer);

        Log.d("WindowEnumeration: ", "onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        // Register the event type
        AccessibilityServiceInfo info = getServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
        setServiceInfo(info);

        setContext(getApplicationContext());

        this.windows = new Windows();
        this.utils = new Utils();
        this.permissions = new Permissions(this.context, this.activity);
        this.bufferIndex = 0;

        this.duration = 1 * 60 * 1000; // 2 minutes in millisecond
        this.startTime = System.currentTimeMillis();
        this.jsonBuffer = "";

        Log.d("WindowEnumeration: ", "onServiceConnected " + info);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}