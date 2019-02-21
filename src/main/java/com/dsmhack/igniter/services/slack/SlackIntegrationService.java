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
import com.github.seratch.jslack.api.methods.request.channels.ChannelsCreateRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsInviteRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsKickRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersLookupByEmailRequest;
import com.github.seratch.jslack.api.methods.response.channels.*;
import com.github.seratch.jslack.api.model.Channel;

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
      if (getChannelIfExists(teamName).isPresent()) {
        throw new ActionNotRequiredException("Channel with name'%s' already exists in slack");
      }
      ChannelsCreateRequest request = ChannelsCreateRequest.builder()
          .name(teamName)
          .token(slackProperties.getOAuthKey())
          .build();
      ChannelsCreateResponse response = slack.methods().channelsCreate(request);
      verifyResponse(response, String.format("Failed to create slack channel of name:'%s'.", teamName));
    } catch (SlackApiException | IOException e) {
      throw new DataConfigurationException(String.format("Failed to create slack channel of name:'%s'", teamName), e);
    }
  }

  @Override
  public TeamValidation validateTeam(String teamName) throws DataConfigurationException {
    Channel channel = getChannel(teamName);

    TeamValidation teamValidation = new TeamValidation();
    teamValidation.setTeamName(channel.getName());
    teamValidation.getAncilaryDetails().put("TeamConfiguration", channel.toString());
    teamValidation.setMembers(channel.getMembers());
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

  private Channel getChannel(String teamName) throws DataConfigurationException {
    return getChannelIfExists(teamName)
        .orElseThrow(() -> new DataConfigurationException(format("There is no channel with a name of '%s'", teamName)));
  }

  private Optional<Channel> getChannelIfExists(String teamName) throws DataConfigurationException {
    return getChannels().stream()
        .filter(g -> g.getName().equals(teamName))
        .findFirst();
  }

  private List<Channel> getChannels() throws DataConfigurationException {
    try {
      ChannelsListRequest request = ChannelsListRequest.builder()
          .token(slackProperties.getOAuthKey())
          .build();
      ChannelsListResponse response = slack.methods().channelsList(request);
      verifyResponse(response, "Error fetching slack channels.");
      return response.getChannels();
    } catch (SlackApiException | IOException e) {
      throw new DataConfigurationException("Error fetching slack channels.", e);
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
      ChannelsKickRequest request = ChannelsKickRequest.builder()
          .channel(getChannel(teamName).getId())
          .user(getUserIdByEmail(userEmail))
          .token(slackProperties.getOAuthKey())
          .build();
      ChannelsKickResponse response = slack.methods().channelsKick(request);
      verifyResponse(response, format("Error removing user '%s' from team '%s'", userEmail, teamName));
    } catch (SlackApiException | IOException e) {
      throw new DataConfigurationException(format("Error removing user '%s' from team '%s'", userEmail, teamName), e);
    }
  }

  private void addUserToTeam(String teamName, String userEmail) throws DataConfigurationException {
    try {
      ChannelsInviteRequest request = ChannelsInviteRequest.builder()
          .channel(getChannel(teamName).getId())
          .token(slackProperties.getOAuthKey())
          .user(getUserIdByEmail(userEmail))
          .build();
      ChannelsInviteResponse response = slack.methods().channelsInvite(request);
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
