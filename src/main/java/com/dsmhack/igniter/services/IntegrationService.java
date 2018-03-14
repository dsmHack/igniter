package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.User;

import java.util.Map;

public interface IntegrationService {
    String getIntegrationServiceName();

    void createTeam(String teamName);
    Map<String,String> validateTeamName(String team);

    void addUserToTeam(String teamName, User user);
}
