package com.example.demo.exceptions;

public class CandyNotFoundException extends RuntimeException {
    public CandyNotFoundException(String message) {
        super(message);
    }

    public CandyNotFoundException(){
        super("Candy not Found");
    }
}
