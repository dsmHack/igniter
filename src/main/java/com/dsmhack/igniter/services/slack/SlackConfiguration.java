package com.dsmhack.igniter.services.slack;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackConfiguration {

  @Bean
  @ConfigurationProperties(prefix = "igniter.slack")
  public SlackProperties slackProperties() {
    return new SlackProperties();
  }

  @Bean
  @ConditionalOnProperty(name = "igniter.slack.enabled", havingValue = "true")
  public SlackIntegrationService slackIntegrationService() {
    return new SlackIntegrationService(slackProperties());
  }

}
