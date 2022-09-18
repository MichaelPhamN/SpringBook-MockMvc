package com.example.mockmvcexampletwo.exception;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String description) {
        super(description);
    }
}
