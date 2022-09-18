package com.example.mockmvcexampletwo.exception;

public class DataAccessException extends RuntimeException{
    public DataAccessException(String description) {
        super(description);
    }
}
