package com.dsmhack.igniter.services;

import java.util.Map;

public interface IntegrationService {
    String getIntegrationServiceName();

    void createTeam(String teamName);
    Map<String,String> validateTeamName(String team);
}
