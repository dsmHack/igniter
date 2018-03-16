package com.dsmhack.igniter.services.exceptions;

public class DataConfigurationException extends Throwable {

    public DataConfigurationException(String format) {
        super(format);
    }

    public DataConfigurationException(String format, DataConfigurationException e) {
        super(format, e);
    }
}
