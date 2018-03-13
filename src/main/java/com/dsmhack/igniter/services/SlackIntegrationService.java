package com.dsmhack.igniter.services;

import org.springframework.stereotype.Service;

@Service
public class SlackIntegrationService implements IntegrationService {
    @Override
    public String getIntegrationServiceName() {
        return "slack";
    }

    @Override
    public void createTeam(String teamName) {

    }
}
