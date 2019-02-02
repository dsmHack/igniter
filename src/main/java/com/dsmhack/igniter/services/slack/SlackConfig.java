package com.dsmhack.igniter.services.slack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "igniter.slack")
public class SlackConfig {
  private String clientId;
  private String clientSecret;
  private String oAuthKey;
}
