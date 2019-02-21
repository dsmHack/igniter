package com.dsmhack.igniter.services.exceptions;

public class ActionNotRequiredException extends IntegrationServiceException {
    public ActionNotRequiredException(String format) {
        super(format);
    }
}
