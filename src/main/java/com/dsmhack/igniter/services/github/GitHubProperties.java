package com.dsmhack.igniter.services.github;

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
@ConfigurationProperties(prefix = "igniter.github")
public class GitHubProperties {
  private String oAuthKey;
  private String clientId;
  private String secretKey;
  private String orgName;
  private String prefix;
}
