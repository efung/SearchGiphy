package com.github.efung.searchgiphy.service;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * @author efung on 2015 Sep 16
 */
public class ApiKeyQueryParamInterceptor implements Interceptor {
    private String apiKey;

    public ApiKeyQueryParamInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl authUrl = originalRequest.httpUrl().newBuilder().addQueryParameter("api_key", apiKey).build();
        Request authRequest = originalRequest.newBuilder().url(authUrl).build();
        return chain.proceed(authRequest);
    }
}
