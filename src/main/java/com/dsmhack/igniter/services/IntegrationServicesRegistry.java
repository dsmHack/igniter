package com.dsmhack.igniter.services;

import com.dsmhack.igniter.IgniterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegrationServicesRegistry {

    private IgniterProperties igniterProperties;

    private final List<IntegrationService> availableServices;

    @Autowired
    public IntegrationServicesRegistry(IgniterProperties igniterProperties, List<IntegrationService> availableServices) {
        this.igniterProperties = igniterProperties;
        this.availableServices = availableServices;
    }

    public List<IntegrationService> getActiveIntegrationServices() {
        return this.availableServices.stream()
                .filter(integrationService -> igniterProperties.isActiveIntegration(integrationService.getIntegrationServiceName()))
                .collect(Collectors.toList());
    }

    public void activateIntegrationService(String integrationServiceName) {
        if (!igniterProperties.isActiveIntegration(integrationServiceName)) {
            igniterProperties.getActiveIntegrations().add(integrationServiceName);
        }
    }

}
