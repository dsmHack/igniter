package com.dsmhack.igniter.services.exceptions;

import com.github.seratch.jslack.api.methods.SlackApiException;

public class DataConfigurationException extends Throwable {

    public DataConfigurationException(String message) {
        super(message);
    }

    public DataConfigurationException(String message, DataConfigurationException e) {
        super(message, e);
    }

    public DataConfigurationException(String message, SlackApiException e) {
        super(message,e);
    }
}
