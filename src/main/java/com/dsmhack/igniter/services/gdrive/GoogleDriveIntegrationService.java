package com.dsmhack.igniter.services.gdrive;

import com.dsmhack.igniter.models.TeamValidation;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;

public class GoogleDriveIntegrationService implements IntegrationService {

  @Override
  public void createTeam(String teamName) {
    throw new UnsupportedOperationException();
  }

  @Override
  public TeamValidation validateTeam(String team) {
    return null;
  }

  @Override
  public void addUserToTeam(String compositeName, User user) {

  }

  @Override
  public void removeUserFromTeam(String teamName, User user) {

  }

  @Override
  public String getIntegrationName() {
    return "gdrive";
  }

}
