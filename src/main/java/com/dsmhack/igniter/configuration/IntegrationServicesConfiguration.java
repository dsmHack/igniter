package com.dsmhack.igniter.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "igniter")
public class IntegrationServicesConfiguration {

  private List<String> activeIntegrations;
  private String teamPrefix;
  private Integer teamNumber;

  public String getCompositeName(String teamName) {
    return this.teamPrefix + teamName;
  }
}
