package com.dsmhack.igniter.services;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegrationServicesRegistry {

    private IntegrationServicesConfiguration integrationServicesConfiguration;

    private final List<IntegrationService> availableServices;

    @Autowired
    public IntegrationServicesRegistry(IntegrationServicesConfiguration integrationServicesConfiguration, List<IntegrationService> availableServices) {
        this.integrationServicesConfiguration = integrationServicesConfiguration;
        this.availableServices = availableServices;
    }

    public List<IntegrationService> getActiveIntegrationServices() {
        return this.availableServices.stream()
                .filter(integrationService -> integrationServicesConfiguration.getActiveIntegrations().contains(integrationService.getIntegrationServiceName()))
                .collect(Collectors.toList());
    }

    public void activateIntegrationService(String integrationServiceName) {
        if (!integrationServicesConfiguration.getActiveIntegrations().contains(integrationServiceName)) {
            integrationServicesConfiguration.getActiveIntegrations().add(integrationServiceName);
        }
    }


}
