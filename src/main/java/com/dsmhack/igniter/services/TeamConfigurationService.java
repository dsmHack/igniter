package com.dsmhack.igniter.services;

import com.dsmhack.igniter.models.ActionLogger;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TeamConfigurationService {

  private final List<IntegrationService> integrationServices;

  public TeamConfigurationService(List<IntegrationService> integrationServices) {
    this.integrationServices = integrationServices;
  }

  public List<String> createTeams(String teamPrefix, int numberOfTeams) {
    return IntStream.rangeClosed(1, numberOfTeams)
        .mapToObj(teamId -> createTeam(teamPrefix + teamId))
        .collect(Collectors.toList());
  }

  public List<String> createTeams(List<String> teams) {
    return teams.stream()
        .map(this::createTeam)
        .collect(Collectors.toList());
  }

  public String createTeam(String teamName) {
    this.integrationServices
        .forEach((IntegrationService integrationService) -> {
            System.out.println("creating team: " + teamName);
            integrationService.createTeam(teamName);
        });
    return teamName;
  }

  public void addUsersToTeam(String teamName, List<User> users) {
    users.forEach(user -> this.addUserToTeam(teamName, user));
  }

  public void addUserToTeam(String teamName, User user) {
    List<ActionLogger> actions = new ArrayList<>();
    this.integrationServices
        .forEach(integrationService -> {
          ActionLogger actionLogger = new ActionLogger();
          actions.add(actionLogger);
          actionLogger.setIntegrationServiceName(integrationService.getIntegrationName());
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
