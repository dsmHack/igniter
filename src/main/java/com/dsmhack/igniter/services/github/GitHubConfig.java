package com.dsmhack.igniter.services.github;

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
@ConfigurationProperties(prefix = "igniter.github")
public class GitHubConfig {
  private String oAuthKey;
  private String clientId;
  private String secretKey;
  private String orgName;
  private String prefix;
}
