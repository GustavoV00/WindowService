package com.example.helloworld.windowService.network;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostRequest extends CustomRequest {
    private String requestBody;

    protected PostRequest(Builder builder) {
        super(builder);
        this.requestBody = builder.requestBody;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public static class Builder extends CustomRequest.Builder<Builder> {
        private String requestBody;

        public Builder setRequestBody(String requestBody) {
            this.requestBody = requestBody;
            return self();
        }

        @Override
        public PostRequest build() {
            return new PostRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public Request buildRequest() {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        RequestBody requestBody = RequestBody.create(this.requestBody, MediaType.parse("application/json"));
        requestBuilder.post(requestBody);

        return requestBuilder.build();
    }
}
