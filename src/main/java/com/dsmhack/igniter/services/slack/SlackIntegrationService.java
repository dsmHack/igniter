package com.dsmhack.igniter.services.slack;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import com.dsmhack.igniter.models.TeamValidation;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.groups.GroupsCreateRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsInviteRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsKickRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsListRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersLookupByEmailRequest;
import com.github.seratch.jslack.api.methods.response.channels.UsersLookupByEmailResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsCreateResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsInviteResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsListResponse;
import com.github.seratch.jslack.api.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;

import static java.lang.String.format;

@Service
public class SlackIntegrationService implements IntegrationService {
    private final IntegrationServicesConfiguration integrationServicesConfiguration;
    private SlackConfig slackConfig;

    private Slack slack;

    @Autowired
    public SlackIntegrationService(IntegrationServicesConfiguration integrationServicesConfiguration) {
        this.integrationServicesConfiguration = integrationServicesConfiguration;
    }

    @Override
    public String getIntegrationServiceName() {
        return "slack";
    }

    @Override
    public void createTeam(String teamName) throws DataConfigurationException, ActionNotRequiredException {
        GroupsCreateResponse groupsCreateResponse;
        try {
            if(getGroupIfExists(teamName)!=null)
                throw new ActionNotRequiredException("Group with name'%s' already exists in slack");
             groupsCreateResponse = slack.methods().groupsCreate(GroupsCreateRequest.builder().name(teamName).token(slackConfig.getOAuthKey()).build());
        } catch (Exception e) {
            throw new DataConfigurationException(String.format("Failed to create slack group of name:'%s'",teamName),e);
        }
        if(!groupsCreateResponse.isOk() )
            throw new DataConfigurationException(String.format("Failed to create slack group of name:'%s'. Error was:%s",teamName,groupsCreateResponse.getError()));



    }

    @Override
    public TeamValidation validateTeam(String team) throws DataConfigurationException {
        TeamValidation teamValidation = new TeamValidation();

        Group group = null;
        try {
            group = getGroup(team);
        } catch (Exception e) {
            throw new DataConfigurationException("Cannot Validate Team:'%s' ",e);
        }
        teamValidation.setTeamName(group.getName());
        teamValidation.getAncilaryDetails().put("TeamConfiguration",group.toString());
        teamValidation.setMembers(group.getMembers());
        return teamValidation;

    }



    @Override
    public void addUserToTeam(String compositeName, User user) throws DataConfigurationException, IOException {

        try {
            Group group = getGroup(compositeName);
            com.github.seratch.jslack.api.model.User userLookup = getUserByEmail(user);
            GroupsInviteRequest groupsInviteRequest = GroupsInviteRequest.builder().channel(group.getName()).token(slackConfig.getOAuthKey()).user(userLookup.getId()).build();
            GroupsInviteResponse groupsInviteResponse = slack.methods().groupsInvite(groupsInviteRequest);
            if (!groupsInviteResponse.isOk()){
                throw new DataConfigurationException(format( "Error adding user '%s' to team '%s'. Error was:%s" ,user.getEmail(),compositeName,groupsInviteResponse.getError()));
            }
        } catch (SlackApiException e) {
            throw new DataConfigurationException(format( "Error adding user '%s' to team '%s'" ,user.getEmail(),compositeName),e);
        }
    }

    private com.github.seratch.jslack.api.model.User getUserByEmail(User user) throws DataConfigurationException, IOException {
        UsersLookupByEmailRequest userLookup = UsersLookupByEmailRequest.builder().token(slackConfig.getOAuthKey()).email(user.getEmail()).build();
        try {
            UsersLookupByEmailResponse usersLookupByEmailResponse = slack.methods().usersLookupByEmail(userLookup);
            if(!usersLookupByEmailResponse.isOk())
                throw new DataConfigurationException(String.format("Error Fetching slack user with email:'%s'. Error: %s",user.getEmail(),usersLookupByEmailResponse.getError()));
            return usersLookupByEmailResponse.getUser();
        }catch (SlackApiException e) {
            throw new DataConfigurationException(format( "Error fetching user for email: %s",user.getEmail()),e);
        }

    }

    private Group getGroup(String teamName) throws IOException, SlackApiException, DataConfigurationException {
        Group group = getGroupIfExists(teamName);
        if (group == null)
            throw new DataConfigurationException(format("There is no channel with a name of '%s'", teamName));
        return group;
    }

    private Group getGroupIfExists(String compositeName) throws IOException, SlackApiException {
        GroupsListRequest groupsListRequest = GroupsListRequest.builder().token(slackConfig.getOAuthKey()).build();
        GroupsListResponse groupsListResponse = slack.methods().groupsList(groupsListRequest);
        return groupsListResponse.getGroups().stream().filter(g -> g.getName().equals(compositeName)).findFirst().orElse(null);
    }

    @Override
    public void removeUserFromTeam(String teamName, User user) throws IOException, DataConfigurationException, ActionNotRequiredException {
        Group group = null;
        try {
            group = getGroup(teamName);
            com.github.seratch.jslack.api.model.User userByEmail = getUserByEmail(user);
            slack.methods().groupsKick(GroupsKickRequest.builder().channel(group.getId()).user(userByEmail.getId()).token(slackConfig.getOAuthKey()).build());
        } catch (SlackApiException e) {
            throw new DataConfigurationException(format("Error removing user '%s' from team '%s'", user.getEmail(), teamName), e);
        }
    }


    @PostConstruct
    public void configure() throws IOException {
        if(!integrationServicesConfiguration.getActiveIntegrations().contains(this.getIntegrationServiceName())) {
            return;
        }
        this.slackConfig = integrationServicesConfiguration.getKeyContent("slack-credentials.json", SlackConfig.class);
        slack = Slack.getInstance();

    }

}
