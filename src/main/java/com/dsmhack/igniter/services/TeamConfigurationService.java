package com.dsmhack.igniter.services;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TeamConfigurationService {

    private final IntegrationServicesRegistry integrationServicesRegistry;
    private final IntegrationServicesConfiguration integrationServicesConfiguration;

    @Autowired
    public TeamConfigurationService(IntegrationServicesRegistry integrationServicesRegistry, IntegrationServicesConfiguration integrationServicesConfiguration) {
        this.integrationServicesRegistry = integrationServicesRegistry;
        this.integrationServicesConfiguration = integrationServicesConfiguration;
    }


    public void createTeam(String teamName) {
        this.integrationServicesRegistry.getActiveIntegrationServices().forEach(integrationService ->  integrationService.createTeam( integrationServicesConfiguration.getCompositeName( teamName)));
    }


    public void addUserToTeam(String teamName, User user) {
        this.integrationServicesRegistry.getActiveIntegrationServices().forEach(integrationService -> {
            try {
                integrationService.addUserToTeam(integrationServicesConfiguration.getCompositeName(teamName),user);
            } catch (ActionNotRequiredException e) {
                e.printStackTrace();
            } catch (DataConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
