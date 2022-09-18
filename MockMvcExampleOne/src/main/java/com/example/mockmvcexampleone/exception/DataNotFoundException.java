package com.example.mockmvcexampleone.exception;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String description) {
        super(description);
    }
}
