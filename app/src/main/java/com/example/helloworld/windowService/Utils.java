package com.example.helloworld.windowService;

import static com.example.helloworld.windowService.network.CustomRequestFactory.createGetRequest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.helloworld.windowService.network.CustomRequest;
import com.example.helloworld.windowService.network.RequestActions;
import com.example.helloworld.windowService.window.WindowInfo;
import com.example.helloworld.windowService.window.Windows;
import com.example.helloworld.windowService.window.WindowsNodesInfos;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import okhttp3.Request;

public class Utils {

    private RequestActions requestActions;

    public Utils() {

    }

    public String toJson(WindowInfo windowInfo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            return objectMapper.writeValueAsString(windowInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    public void printWindowData(Map<Integer, WindowsNodesInfos> lastWindowsNodes) {
//        int i = 0;
//        if (lastWindowsNodes.size() > 1) {
//            for (Map.Entry<Integer, WindowsNodesInfos> node : lastWindowsNodes.entrySet()) {
//                Log.d("Window: ", "Title: " + node.getKey() + ", Package: " + node.getValue() + ", id: " + i);
//                i += 1;
//            }
//        } else {
//            for (Map.Entry<String, String> node : lastWindowsNodes.entrySet()) {
//                Log.d("FocusedWindow: ", "Title: " + node.getKey() + ", Package: " + node.getValue() + ", id: " + i);
//            }
//        }
//    }

    public boolean compareWindowsInBuffer(List<String> buffer, WindowInfo windowInfo) {
        if (buffer.size() == 0)
            return true;

        try {
            int bufferSize = buffer.size() - 1;
            ObjectMapper objectMapper = new ObjectMapper();
            WindowInfo lastWindow = objectMapper.readValue(buffer.get(bufferSize), WindowInfo.class);

//            if (!lastWindow.getWindowsNodes().equals(windowInfo.getWindowsNodes())
            if (lastWindow.getWindowStatus() != windowInfo.getWindowStatus()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyIfServerIsUp() {
        requestActions = new RequestActions();
        CustomRequest getRequest = createGetRequest("http://172.29.117.71:5000/test");
        Request request = getRequest.buildRequest();
        CompletableFuture<String> future = requestActions.sendRequest(request, getRequest);

        future.thenAccept(response -> {
            Log.d("Response", "Success: " + response);
        }).exceptionally(ex -> {
//            ex.printStackTrace();
            Log.d("Response", "Failure2: " + ex);
            return null;
        });


        return true;
    }

    public boolean bufferIsFull(@NonNull List<String> buffer) {
//        if(buffer.size() >= 10)
        return true;
//        return false;
    }
}
