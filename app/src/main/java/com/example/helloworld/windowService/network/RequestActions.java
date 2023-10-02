package com.example.helloworld.windowService.network;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RequestActions {

    public RequestActions() {

    }

    public CompletableFuture<String> sendRequest(Request request, CustomRequest requestType) {
        OkHttpClient client = new OkHttpClient();
        CompletableFuture<String> future = new CompletableFuture<>();

         client.newCall(request).enqueue(new Callback() {
             @Override
             public void onFailure(@NonNull Call call, @NonNull IOException e) {
                 future.completeExceptionally(e);
             }

             @Override
             public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                 if (response.isSuccessful()) {
                     String responseData = response.body().string();
                     future.complete(responseData);
                 } else {
                     future.completeExceptionally(new IOException("Request failed with code: " + response.code()));
                 }
                 response.close();
             }
         });

         return future;
    }
}
