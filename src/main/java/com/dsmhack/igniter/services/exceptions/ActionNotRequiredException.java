package com.dsmhack.igniter.services.exceptions;

public class ActionNotRequiredException extends Exception {
    public ActionNotRequiredException(String format) {
        super(format);
    }
}
