package com.dsmhack.igniter.services.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Configuration
public class UserImportServiceConfiguration {

  private final static Map<UserFormat, Supplier<UserImportService>> MAP = createMap();

  private final UserFormat userFormat;

  public UserImportServiceConfiguration(@Value("${igniter.user.format}") UserFormat userFormat) {
    this.userFormat = userFormat;
  }

  @Bean
  public UserImportService userImportService() {
    return MAP.getOrDefault(this.userFormat, EventBriteUserImportService::new).get();
  }

  private static Map<UserFormat, Supplier<UserImportService>> createMap() {
    Map<UserFormat, Supplier<UserImportService>> map = new HashMap<>();
    map.put(UserFormat.EVENTBRITE, EventBriteUserImportService::new);
    return map;

  }
}
