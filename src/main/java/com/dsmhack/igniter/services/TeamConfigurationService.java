package com.dsmhack.igniter.services;

import com.dsmhack.igniter.configuration.IntegrationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamConfigurationService {

    private final IntegrationConfiguration integrationConfiguration;

    @Autowired
    public TeamConfigurationService(IntegrationConfiguration integrationConfiguration) {
        this.integrationConfiguration = integrationConfiguration;
    }


    public void createTeam(String teamName) {

    }



}
