package com.dsmhack.igniter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "igniter")
public class IgniterProperties {

  private List<String> activeIntegrations;
  private String teamPrefix;
  private Integer teamNumber;

  public String getCompositeName(int teamId) {
    return this.teamPrefix + teamId;
  }

  public boolean isActiveIntegration(String name) {
    return this.activeIntegrations.contains(name);
  }
}
