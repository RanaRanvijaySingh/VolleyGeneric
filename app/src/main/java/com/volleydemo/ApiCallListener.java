package com.volleydemo;

public interface ApiCallListener {
    public void onSuccess(String response, Object responseObject);
    public void onError(String message);
}
