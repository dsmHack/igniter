package com.dsmhack.igniter.services.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserImportServiceConfiguration {

  @Bean
  public UserImportServiceRegistry userImportServiceRegistry() {
    return new UserImportServiceRegistry()
        .register(UserFormat.EVENTBRITE, new EventBriteUserImportService())
        .register(UserFormat.JSON, new JsonUserImportService(new ObjectMapper()));
  }

}
