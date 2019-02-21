package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.ActionLogger;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TeamConfigurationService {

  private final IntegrationServicesRegistry integrationServicesRegistry;

  @Autowired
  public TeamConfigurationService(IntegrationServicesRegistry integrationServicesRegistry) {
    this.integrationServicesRegistry = integrationServicesRegistry;
  }

  public List<String> createTeams(String teamPrefix, int numberOfTeams) {
    return IntStream.rangeClosed(1, numberOfTeams)
        .mapToObj(teamId -> this.createTeam(teamPrefix + teamId))
        .collect(Collectors.toList());
  }

  public String createTeam(String teamName) {
    this.integrationServicesRegistry.getActiveIntegrationServices()
        .forEach((IntegrationService integrationService) -> {
          try {
            System.out.println("creating team: " + teamName);
            integrationService.createTeam(teamName);
          } catch (DataConfigurationException | ActionNotRequiredException e) {
            e.printStackTrace();
          }
        });
    return teamName;
  }

  public void addUsersToTeam(String teamName, List<User> users) {
    users.forEach(user -> this.addUserToTeam(teamName, user));
  }

  public void addUserToTeam(String teamName, User user) {
    List<ActionLogger> actions = new ArrayList<>();
    this.integrationServicesRegistry.getActiveIntegrationServices().forEach(integrationService -> {
      ActionLogger actionLogger = new ActionLogger();
      actions.add(actionLogger);
      actionLogger.setIntegrationServiceName(integrationService.getIntegrationServiceName());
      actionLogger.setActionAttempted(String.format("Adding user '%s' to team '%s'", user, teamName));
      try {
        System.out.println("Adding User: " + user.getFirstName() + " " + user.getLastName() + " teamName: " + teamName);
        integrationService.addUserToTeam(teamName, user);
      } catch (ActionNotRequiredException e) {
        actionLogger.setWarning(ExceptionUtils.getStackTrace(e));
      } catch (Throwable e) {
        actionLogger.setError(ExceptionUtils.getStackTrace(e));
      }
    });
  }
}
