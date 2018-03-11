package com.dsmhack.igniter.services;

import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class GoogleDriveIntegrationService implements IntegrationService {
    public void createTeam(String teamName) {
        throw new NotImplementedException();
    }

    @Override
    public String getIntegrationServiceName() {
        return "googleDrive";
    }
}
