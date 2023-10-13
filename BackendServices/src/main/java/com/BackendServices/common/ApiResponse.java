package com.BackendServices.common;

public class ApiResponse {
    private int status;
    private Object data;
  

    private String message;
    public ApiResponse() {}

    public ApiResponse(int status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiResponse [status=" + status + ", data=" + data + ", message=" + message + "]";
    }
    }