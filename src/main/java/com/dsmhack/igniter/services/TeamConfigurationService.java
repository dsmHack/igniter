package com.dsmhack.igniter.services;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamConfigurationService {

    private final IntegrationServicesConfiguration integrationServicesConfiguration;

    @Autowired
    public TeamConfigurationService(IntegrationServicesConfiguration integrationServicesConfiguration) {
        this.integrationServicesConfiguration = integrationServicesConfiguration;
    }


    public void createTeam(String teamName) {

    }



}
