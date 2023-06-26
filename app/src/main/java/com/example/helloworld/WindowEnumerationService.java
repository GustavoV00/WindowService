package com.example.helloworld;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class WindowEnumerationService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        // Verify if the root node is and accessibility valid
        switch (eventType) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                if (rootNode != null) {
                    // Call the function that runs through the window list
                    enumerateWindows(rootNode);
                }
                break;
        }

    }

    private void enumerateWindows(AccessibilityNodeInfo node) {
        // verify if the node represents a window
        if (node.getClassName().equals("android.widget.FrameLayout")) {
            // Here we can extract info about the windows, like title and the packet name
            String windowTitle = node.getContentDescription() != null && !node.getContentDescription().toString().isEmpty() ? node.getContentDescription().toString() : "";
            String packageName = node.getPackageName() != null ? node.getPackageName().toString() : "Package not available";

            if (windowTitle.equals("")) {
                String[] result = packageName.split("\\.");
                windowTitle = result[result.length - 1];
            }

            // Just log the infos
            Log.d("WindowEnumeration: ", "Title: " + windowTitle + ", Package: " + packageName);
        }

        // Go trough the child nodes
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo childNode = node.getChild(i);
            if (childNode != null) {
                enumerateWindows(childNode);
            }
        }
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

        Log.d("WindowEnumeration: ", "onServiceConnected " + info);
    }
}
