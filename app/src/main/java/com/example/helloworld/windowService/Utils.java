package com.example.helloworld.windowService;

import android.util.Log;

import static com.example.helloworld.windowService.network.CustomRequestFactory.createGetRequest;
import static com.example.helloworld.windowService.network.CustomRequestFactory.createPostRequest;

import androidx.annotation.NonNull;

import com.example.helloworld.windowService.network.CustomRequest;
import com.example.helloworld.windowService.network.GetRequest;
import com.example.helloworld.windowService.network.RequestActions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {

    private RequestActions requestActions;

    public Utils() {

    }

    public String hashMapToJson(Map<String, String> hashMap) {
        JSONObject jsonObject = new JSONObject();

        try {
            for (String key : hashMap.keySet()) {
                String value = hashMap.get(key);
                jsonObject.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("Json: ", jsonObject.toString());
        return jsonObject.toString();
    }

    public void printWindowData(Map<String, String> lastWindowsNodes) {
        int i = 0;
        if (lastWindowsNodes.size() > 1) {
            for (Map.Entry<String, String> node : lastWindowsNodes.entrySet()) {
                Log.d("Window: ", "Title: " + node.getKey() + ", Package: " + node.getValue() + ", id: " + i);
                i += 1;
            }
        } else {
            for (Map.Entry<String, String> node : lastWindowsNodes.entrySet()) {
                Log.d("FocusedWindow: ", "Title: " + node.getKey() + ", Package: " + node.getValue() + ", id: " + i);
            }
        }
    }

    public boolean verifyIfServerIsUp() {
        requestActions = new RequestActions();
        CustomRequest getRequest = createGetRequest("http://172.29.117.71:5000/test");
        Request request = getRequest.buildRequest();
        CompletableFuture<String> future = requestActions.sendRequest(request, getRequest);

        future.thenAcceptAsync(response -> {
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
