package com.dsmhack.igniter.services.slack;

import com.dsmhack.igniter.models.TeamValidation;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
import com.dsmhack.igniter.services.exceptions.IntegrationServiceException;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.methods.request.groups.GroupsCreateRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsInviteRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsKickRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsListRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersLookupByEmailRequest;
import com.github.seratch.jslack.api.methods.response.channels.UsersLookupByEmailResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsCreateResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsInviteResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsKickResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsListResponse;
import com.github.seratch.jslack.api.model.Group;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class SlackIntegrationService implements IntegrationService {

  private final SlackProperties slackProperties;

  private Slack slack;

  public SlackIntegrationService(SlackProperties slackProperties) {
    this.slackProperties = slackProperties;
  }

  @Override
  public String getIntegrationName() {
    return "slack";
  }

  @Override
  public void createTeam(String teamName) throws IntegrationServiceException {
    try {
      if (getGroupIfExists(teamName).isPresent()) {
        throw new ActionNotRequiredException("Group with name'%s' already exists in slack");
      }
      GroupsCreateRequest request = GroupsCreateRequest.builder()
          .name(teamName)
          .token(slackProperties.getOAuthKey())
          .build();
      GroupsCreateResponse response = slack.methods().groupsCreate(request);
      verifyResponse(response, String.format("Failed to create slack group of name:'%s'.", teamName));
    } catch (SlackApiException | IOException e) {
      throw new DataConfigurationException(String.format("Failed to create slack group of name:'%s'", teamName), e);
    }
  }

  @Override
  public TeamValidation validateTeam(String teamName) throws DataConfigurationException {
    Group group = getGroup(teamName);

    TeamValidation teamValidation = new TeamValidation();
    teamValidation.setTeamName(group.getName());
    teamValidation.getAncilaryDetails().put("TeamConfiguration", group.toString());
    teamValidation.setMembers(group.getMembers());
    return teamValidation;
  }

  @Override
  public void addUserToTeam(String teamName, User user) throws DataConfigurationException {
    addUserToTeam(teamName, user.getSlackEmail());
  }

  @Override
  public void removeUserFromTeam(String teamName, User user) throws DataConfigurationException {
    removeUserFromTeam(teamName, user.getSlackEmail());
  }

  @PostConstruct
  public void configure() {
    slack = Slack.getInstance();
  }

  private Group getGroup(String teamName) throws DataConfigurationException {
    return getGroupIfExists(teamName)
        .orElseThrow(() -> new DataConfigurationException(format("There is no group with a name of '%s'", teamName)));
  }

  private Optional<Group> getGroupIfExists(String teamName) throws DataConfigurationException {
    return getGroups().stream()
        .filter(g -> g.getName().equals(teamName))
        .findFirst();
  }

  private List<Group> getGroups() throws DataConfigurationException {
    try {
      GroupsListRequest request = GroupsListRequest.builder()
          .token(slackProperties.getOAuthKey())
          .build();
      GroupsListResponse response = slack.methods().groupsList(request);
      verifyResponse(response, "Error fetching slack groups.");
      return response.getGroups();
    } catch (SlackApiException | IOException e) {
      throw new DataConfigurationException("Error fetching slack groups.", e);
    }
  }

  private String getUserIdByEmail(String email) throws DataConfigurationException {
    try {
      UsersLookupByEmailRequest request = UsersLookupByEmailRequest.builder()
          .token(slackProperties.getOAuthKey())
          .email(email)
          .build();
      UsersLookupByEmailResponse response = slack.methods().usersLookupByEmail(request);
      verifyResponse(response, String.format("Error fetching slack user with email: %s", email));
      return response.getUser().getId();
    } catch (SlackApiException | IOException e) {
      throw new DataConfigurationException(format("Error fetching slack user for email: %s", email), e);
    }
  }

  private void removeUserFromTeam(String teamName, String userEmail) throws DataConfigurationException {
    try {
      GroupsKickRequest request = GroupsKickRequest.builder()
          .channel(getGroup(teamName).getId())
          .user(getUserIdByEmail(userEmail))
          .token(slackProperties.getOAuthKey())
          .build();
      GroupsKickResponse response = slack.methods().groupsKick(request);
      verifyResponse(response, format("Error removing user '%s' from team '%s'", userEmail, teamName));
    } catch (SlackApiException | IOException e) {
      throw new DataConfigurationException(format("Error removing user '%s' from team '%s'", userEmail, teamName), e);
    }
  }

  private void addUserToTeam(String teamName, String userEmail) throws DataConfigurationException {
    try {
      GroupsInviteRequest request = GroupsInviteRequest.builder()
          .channel(getGroup(teamName).getId())
          .token(slackProperties.getOAuthKey())
          .user(getUserIdByEmail(userEmail))
          .build();
      GroupsInviteResponse response = slack.methods().groupsInvite(request);
      verifyResponse(response, format("Error adding user '%s' to team '%s'", userEmail, teamName));
    } catch (SlackApiException | IOException e) {
      throw new DataConfigurationException(format("Error adding user '%s' to team '%s'", userEmail, teamName), e);
    }
  }

  private void verifyResponse(SlackApiResponse response, String message) throws DataConfigurationException {
    if (!response.isOk()) {
      throw new DataConfigurationException(message + " Error: " + response.getError());
    }
  }
}
