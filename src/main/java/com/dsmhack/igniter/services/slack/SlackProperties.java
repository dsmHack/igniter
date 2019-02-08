package com.dsmhack.igniter.services.slack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "igniter.slack")
public class SlackProperties {
  private String clientId;
  private String clientSecret;
  private String oAuthKey;
}
