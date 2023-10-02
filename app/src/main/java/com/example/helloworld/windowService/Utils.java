package com.example.helloworld.windowService;

import static com.example.helloworld.windowService.network.CustomRequestFactory.createGetRequest;
import static com.example.helloworld.windowService.network.CustomRequestFactory.createPostRequest;

import android.util.Log;

import com.example.helloworld.windowService.network.CustomRequest;
import com.example.helloworld.windowService.network.RequestActions;
import com.example.helloworld.windowService.window.WindowInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import okhttp3.Request;

public class Utils {

    private RequestActions requestActions;

    public Utils() {

    }

    public String toJson(List<WindowInfo> buffer) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            return objectMapper.writeValueAsString(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean compareWindowsInBuffer(List<WindowInfo> buffer, WindowInfo windowInfo) {
        if (buffer.size() == 0)
            return true;

        try {
            int bufferSize = buffer.size() - 1;
            WindowInfo lastElem = buffer.get(bufferSize);

            if (windowInfo.getWindowStatus() == lastElem.getWindowStatus()) {
                buffer.remove(bufferSize);
                buffer.add(windowInfo);
                return false;
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyIfServerIsUp() {
        requestActions = new RequestActions();
        CustomRequest getRequest = createGetRequest("http://18.189.157.240/windows");
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

    public boolean timeCheckUp(long startTime, long duration) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;
        if (elapsedTime >= duration) {
            System.out.println("Timer expired.");
            return true;
        } else {
            return false;
        }
    }

    public boolean bufferIsFull(List<WindowInfo> buffer) {
        buffer.removeIf(element -> element.getWindowsNodes().size() == 0);
        if (buffer.size() >= 5)
            return true;
        return false;
    }

    public String convertAllBufferToJson(List<WindowInfo> buffer, String bufferJson) {
        bufferJson = bufferJson + "" + toJson(buffer);
        return bufferJson;
    }
}
