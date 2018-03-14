package com.dsmhack.igniter.services;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SlackIntegrationService implements IntegrationService {
    @Override
    public String getIntegrationServiceName() {
        return "slack";
    }

    @Override
    public void createTeam(String teamName) {

    }

    @Override
    public Map<String, String> validateTeamName(String team) {
        return null;
    }
}
