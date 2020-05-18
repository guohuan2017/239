package com.gyx.sp.controller.iwant;

public interface IOListener<T> {
    public void onCompleted(T result);
    public void onLoading(T readedPart, long current, long length);
    public void onInterrupted();
    public void onFail(String errorMsg);
}

