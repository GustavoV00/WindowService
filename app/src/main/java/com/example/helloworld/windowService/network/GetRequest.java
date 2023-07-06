package com.example.helloworld.windowService.network;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class GetRequest extends CustomRequest {


    public GetRequest(Builder builder) {
        super(builder);
    }

    public static class Builder extends CustomRequest.Builder<Builder> {

        @Override
        public GetRequest build() {
            return new GetRequest(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

    public Request buildRequest() {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .get();


        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        return requestBuilder.build();
    }

}
