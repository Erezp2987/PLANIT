package com.erez_p.model;


public interface ResponseCallback {
    void onResponse(String response);
    void onError(Throwable throwable);
}