package com.example.mockmvcexampleone.exception;

public class InvalidDataRequestException extends RuntimeException{
    public InvalidDataRequestException(String description) {
        super(description);
    }
}
