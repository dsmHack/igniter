package com.dsmhack.igniter.services.exceptions;

public class IntegrationServiceException extends RuntimeException {

  public IntegrationServiceException(String message) {
    super(message);
  }

  public IntegrationServiceException(String message, Throwable cause) {
    super(message, cause);
  }

}
