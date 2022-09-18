package com.example.mockmvcexampleone.exception;

public class DataAccessException extends RuntimeException{
    public DataAccessException(String description) {
        super(description);
    }
}
