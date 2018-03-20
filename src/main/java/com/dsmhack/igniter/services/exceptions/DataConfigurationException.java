package com.dsmhack.igniter.services.exceptions;

import com.github.seratch.jslack.api.methods.SlackApiException;

public class DataConfigurationException extends Throwable {

    public DataConfigurationException(String message) {
        super(message);
    }

    public DataConfigurationException(String message, Exception e) {
        super(message,e);
    }
}
