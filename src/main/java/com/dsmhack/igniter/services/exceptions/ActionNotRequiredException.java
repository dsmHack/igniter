package com.dsmhack.igniter.services.exceptions;

public class ActionNotRequiredException extends Throwable {
    public ActionNotRequiredException(String format) {
        super(format);
    }
}
