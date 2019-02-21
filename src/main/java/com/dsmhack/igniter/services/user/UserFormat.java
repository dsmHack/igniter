package com.dsmhack.igniter.services.user;

public enum UserFormat {
  EVENTBRITE("EventBrite"),
  JSON("JSON");

  private final String displayName;

  UserFormat(String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }
}
