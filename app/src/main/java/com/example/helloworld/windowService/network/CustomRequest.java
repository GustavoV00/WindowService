package com.example.helloworld.windowService.network;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public abstract class CustomRequest {
    private OkHttpClient client = new OkHttpClient();
    protected String url;
    protected Map<String, String> headers;
    public CustomRequest(Builder<?> builder) {
        this.url = builder.url;
        this.headers = builder.headers;
    }

    public OkHttpClient getClient() {
        return this.client;
    }
    public abstract Request buildRequest();

    public static abstract class Builder<T extends Builder<T>> {
        public Map<String, String> headers;
        private String url;

        public T setUrl(String url) {
            this.url = url;
            return self();
        }

        public T setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return self();
        }

        public abstract CustomRequest build();
        protected abstract T self();

    }

}
