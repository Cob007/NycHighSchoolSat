package com.nycschools.a20220131_michealadeniyi_nycschools.network;

public class ApiResponse<R> {
    public R response;
    private Throwable error;

    public ApiResponse(R response) {
        this.response = response;
        this.error = null;
    }

    public ApiResponse(Throwable error) {
        this.error = error;
        this.response = null;
    }

    public R getResponse() {
        return response;
    }

    public void setResponse(R response) {
        this.response = response;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
