package com.dsmhack.igniter.services.github;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitHubConfiguration {

  @Bean
  @ConfigurationProperties(prefix = "igniter.github")
  public GitHubProperties gitHubProperties() {
    return new GitHubProperties();
  }

  @Bean
  @ConditionalOnProperty(name = "igniter.github.enabled", havingValue = "true")
  public GitHubIntegrationService gitHubIntegrationService() {
    return new GitHubIntegrationService(gitHubProperties());
  }

}
