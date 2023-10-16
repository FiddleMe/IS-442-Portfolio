package com.BackendServices.User.exception;

public class UserExeption extends RuntimeException{
    public UserExeption(String message) {
        super(message);
    }
    public UserExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
