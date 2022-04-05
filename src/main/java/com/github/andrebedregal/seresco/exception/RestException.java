package com.github.andrebedregal.seresco.exception;

public class RestException extends Exception {

    private final String details;

    public RestException(String message, String details) {
        super(message);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

}
