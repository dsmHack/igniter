package com.dsmhack.igniter.services;

import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

@Service
public class GoogleDriveIntegrationService implements IntegrationService {
    public void createTeam(String teamName) {
        throw new NotImplementedException();
    }

    @Override
    public Map<String, String> validateTeamName(String team) {
        return null;
    }

    @Override
    public String getIntegrationServiceName() {
        return "googleDrive";
    }
}
