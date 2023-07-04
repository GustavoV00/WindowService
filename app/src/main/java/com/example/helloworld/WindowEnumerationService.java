package com.example.helloworld;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.helloworld.windowService.Permissions;
import com.example.helloworld.windowService.Utils;
import com.example.helloworld.windowService.Windows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;


public class WindowEnumerationService extends AccessibilityService {

    private Windows windows;
    private Activity activity;
    private Context context;
    private Permissions permissions;
    private Utils utils;
    List<String> buffer = new ArrayList<>();


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
                    Map<String, String> windowNodes = new HashMap<>();
                    if (rootNode != null) {
                        this.windows.enumerateWindows(rootNode, windowNodes);
                        this.windows.compareLatestWindowsEvents(windowNodes);
                        buffer.add(this.utils.hashMapToJson(windowNodes));
                        if (this.utils.verifyIfServerIsUp() && utils.bufferIsFull(buffer))
                            this.windows.sendWindowsToServer(buffer);
                        else {
                            Log.d("Server", "Server is down");
                        }

                        windowNodes.clear();
                    }
                    break;
            }

        } else {
            this.permissions.requestCorrectPermissions();
        }

        Log.d("EventType: ", "");
        rootNode.recycle(); // recycle root node
    }


    @Override
    public void onInterrupt() {
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

        Log.d("WindowEnumeration: ", "onServiceConnected " + info);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}