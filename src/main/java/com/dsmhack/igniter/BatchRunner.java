package com.dsmhack.igniter;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.TeamConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchRunner {

  private final TeamConfigurationService teamConfigurationService;

  @Autowired
  public BatchRunner(TeamConfigurationService teamConfigurationService) {
    this.teamConfigurationService = teamConfigurationService;
  }

  public void onboardEveryone(String teamPrefix, int numberOfTeams, List<User> users) {
    teamConfigurationService.createTeams(teamPrefix, numberOfTeams)
        .forEach(teamName -> teamConfigurationService.addUsersToTeam(teamName, users));
  }
}
