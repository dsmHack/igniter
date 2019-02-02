package com.dsmhack.igniter.services.exceptions;

public class DataConfigurationException extends Exception {

    public DataConfigurationException(String message) {
        super(message);
    }

    public DataConfigurationException(String message, Exception e) {
        super(message,e);
    }
}
