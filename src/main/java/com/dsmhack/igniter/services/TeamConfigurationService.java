package com.dsmhack.igniter.services;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamConfigurationService {

    private final IntegrationServicesRegistry integrationServicesRegistry;

    @Autowired
    public TeamConfigurationService(IntegrationServicesRegistry integrationServicesRegistry) {
        this.integrationServicesRegistry = integrationServicesRegistry;
    }


    public void createTeam(String teamName) {
        this.integrationServicesRegistry.getActiveIntegrationServices().forEach(integrationService -> integrationService.createTeam(teamName));
    }



}
