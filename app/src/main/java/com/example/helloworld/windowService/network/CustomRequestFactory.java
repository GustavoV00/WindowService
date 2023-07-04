package com.example.helloworld.windowService.network;

import java.util.Map;

public class CustomRequestFactory {
    public CustomRequestFactory() {

    }

    public static CustomRequest createPostRequest(String url, String requestBody) {
        return new PostRequest.Builder()
                .setUrl(url)
                .setRequestBody(requestBody)
                .build();
    }

    public static CustomRequest createPostRequest(String url, String requestBody, Map<String, String> headers) {
        return new PostRequest.Builder()
                .setUrl(url)
                .setHeaders(headers)
                .setRequestBody(requestBody)
                .build();
    }

    public static CustomRequest createGetRequest(String url) {
        return new GetRequest.Builder()
                .setUrl(url)
                .build();
    }

    public static CustomRequest createGetRequest(String url, Map<String, String> headers) {
        return new GetRequest.Builder()
                .setUrl(url)
                .setHeaders(headers)
                .build();
    }
}
