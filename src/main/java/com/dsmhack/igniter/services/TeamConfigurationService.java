package com.dsmhack.igniter.services;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import com.dsmhack.igniter.models.ActionLogger;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        this.integrationServicesRegistry.getActiveIntegrationServices()
                .forEach((IntegrationService integrationService) -> {
                    try {
                        System.out.println("creating team: " + teamName);
                        integrationService.createTeam(teamName);
                    } catch (DataConfigurationException e) {
                        e.printStackTrace();
                    } catch (ActionNotRequiredException e) {
                        e.printStackTrace();
                    }
                });
    }


    public void addUserToTeam(String teamName, User user) {
        List<ActionLogger> actions = new ArrayList<>();
        this.integrationServicesRegistry.getActiveIntegrationServices().forEach(integrationService -> {
            ActionLogger actionLogger = new ActionLogger();
            actions.add(actionLogger);
            actionLogger.setIntegrationServiceName(integrationService.getIntegrationServiceName());
            actionLogger.setActionAttempted(String.format("Adding user '%s' to team '%s'",user,teamName));
            try {
                System.out.println("Adding User: " + user.getEmail() + " teamName: " + teamName);
                integrationService.addUserToTeam(teamName,user);
            } catch (ActionNotRequiredException e) {
                actionLogger.setWarning(ExceptionUtils.getStackTrace(e));
            } catch (Throwable e) {
                actionLogger.setError(ExceptionUtils.getStackTrace(e));
            }
        });
    }
}
