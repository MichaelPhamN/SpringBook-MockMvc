package com.example.mockmvcexampletwo.exception;

public class InvalidDataRequestException extends RuntimeException{
    public InvalidDataRequestException(String description) {
        super(description);
    }
}
