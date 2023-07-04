package com.example.helloworld.windowService.network;

public class RequestActions {
    public interface HttpRequestListener {
        void onResponse(String response);
        void onError(Exception e);
    }

//    public void makeRequest(String url, HttpRequestListener listener) {
//        GlobalScope.launch(Dispatchers.IO) {
//            try {
//                Request request = new Request.Builder()
//                        .url(url)
//                        .build();
//
//                Response response = client.newCall(request).execute();
//                if (response.isSuccessful()) {
//                    String responseData = response.body().string();
//                    withContext(Dispatchers.Main) {
//                        listener.onResponse(responseData);
//                    }
//                } else {
//                    throw new Exception("Request failed with code: " + response.code());
//                }
//            } catch (Exception e) {
//                withContext(Dispatchers.Main) {
//                    listener.onError(e);
//                }
//            }
//        }
//    }
}
