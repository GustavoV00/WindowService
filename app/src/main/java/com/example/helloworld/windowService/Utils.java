package com.example.helloworld.windowService;

import android.util.Log;

import static com.example.helloworld.windowService.network.CustomRequestFactory.createGetRequest;
import static com.example.helloworld.windowService.network.CustomRequestFactory.createPostRequest;

import androidx.annotation.NonNull;

import com.example.helloworld.windowService.network.CustomRequest;
import com.example.helloworld.windowService.network.GetRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {

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
        try {
            CustomRequest getRequest = createGetRequest("http://127.0.0.1:5000/test");
            Request request = getRequest.buildRequest();

            Response response = getRequest.getClient().newCall(request).execute();
            Log.d("VerifyIfServerIsUp: ", "Response: " + response);
            return true;

        } catch (IOException e) {
            Log.d("VerifyIfServerIsUp: ", "Exception: " + e);
            return false;
        }
    }

    public boolean bufferIsFull(@NonNull List<String> buffer) {
        if(buffer.size() >= 10)
            return true;
        return false;
    }
}
