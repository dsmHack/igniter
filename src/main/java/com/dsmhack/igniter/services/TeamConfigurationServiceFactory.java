package com.dsmhack.igniter.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamConfigurationServiceFactory {

  private final IntegrationServicesRegistry integrationServicesRegistry;

  public TeamConfigurationServiceFactory(IntegrationServicesRegistry integrationServicesRegistry) {
    this.integrationServicesRegistry = integrationServicesRegistry;
  }

  public TeamConfigurationService create() {
    return new TeamConfigurationService(integrationServicesRegistry.getIntegrations());
  }

  public TeamConfigurationService create(List<String> integrations) {
    List<IntegrationService> integrationServices = integrationServicesRegistry.getIntegrations().stream()
        .filter(integrationService -> integrations.contains(integrationService.getIntegrationName()))
        .collect(Collectors.toList());

    return new TeamConfigurationService(integrationServices);
  }

}
