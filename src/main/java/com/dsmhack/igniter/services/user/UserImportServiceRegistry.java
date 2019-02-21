package com.dsmhack.igniter.services.user;

import java.util.*;

public class UserImportServiceRegistry {

  private final Map<UserFormat, UserImportService> services;

  public UserImportServiceRegistry() {
    this.services = new HashMap<>();
  }

  public Optional<UserImportService> getService(UserFormat userFormat) {
    return Optional.of(this.services.get(userFormat));
  }

  public List<UserFormat> getSupportedFormats() {
    return new ArrayList<>(this.services.keySet());
  }

  public UserImportServiceRegistry register(UserFormat userFormat, UserImportService service) {
    this.services.put(userFormat, service);
    return this;
  }

}
