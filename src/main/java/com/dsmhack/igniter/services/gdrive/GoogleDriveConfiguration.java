package com.dsmhack.igniter.services.gdrive;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleDriveConfiguration {

  @Bean
  @ConditionalOnProperty(name = "igniter.google-drive.enabled", havingValue = "true")
  public GoogleDriveIntegrationService googleDriveIntegrationService() {
    return new GoogleDriveIntegrationService();
  }

}
