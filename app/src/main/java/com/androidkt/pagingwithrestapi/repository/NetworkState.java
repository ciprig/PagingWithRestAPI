package com.androidkt.pagingwithrestapi.repository;

/**
 * Created by brijesh on 25/12/17.
 */


public class NetworkState {
    public static final NetworkState LOADED = new NetworkState(Status.SUCCESS, "Success");
    public static final NetworkState LOADING = new NetworkState(Status.RUNNING, "Running");
    private final Status status;
    private final String msg;

    private NetworkState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static NetworkState failed(String msg) {
        return new NetworkState(Status.FAILED, msg);
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
