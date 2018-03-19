package com.dsmhack.igniter.services.slack;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.IntegrationService;
import com.dsmhack.igniter.services.exceptions.ActionNotRequiredException;
import com.dsmhack.igniter.services.exceptions.DataConfigurationException;
import com.dsmhack.igniter.services.github.GitHubConfig;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.impl.MethodsClientImpl;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsCreateRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsCreateRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsInviteRequest;
import com.github.seratch.jslack.api.methods.request.groups.GroupsListRequest;
import com.github.seratch.jslack.api.methods.request.oauth.OAuthAccessRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersIdentityRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersInfoRequest;
import com.github.seratch.jslack.api.methods.request.users.UsersLookupByEmailRequest;
import com.github.seratch.jslack.api.methods.response.channels.UsersLookupByEmailResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsCreateResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsInviteResponse;
import com.github.seratch.jslack.api.methods.response.groups.GroupsListResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersIdentityResponse;
import com.github.seratch.jslack.api.model.Group;
import com.github.seratch.jslack.api.rtm.RTMClient;
import com.github.seratch.jslack.common.http.SlackHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

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
    public void createTeam(String teamName) {

        GroupsCreateRequest groupsCreateRequest = GroupsCreateRequest.builder().name(teamName).token(slackConfig.getOAuthKey()).build();
        GroupsCreateResponse groupsCreateResponse = null;
        try {
            groupsCreateResponse = slack.methods().groupsCreate(groupsCreateRequest);

//            slack.methods().channelsCreate(build)
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SlackApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Map<String, String> validateTeamName(String team) {
        return null;
    }



    @Override
    public void addUserToTeam(String compositeName, User user) throws DataConfigurationException, IOException {

        try {
            Group group = getGroup(compositeName);
            UsersLookupByEmailResponse usersLookupByEmailResponse = getUserByEmail(user);
            GroupsInviteRequest groupsInviteRequest = GroupsInviteRequest.builder().channel(group.getName()).token(slackConfig.getOAuthKey()).user(usersLookupByEmailResponse.getUser().getId()).build();
            GroupsInviteResponse groupsInviteResponse = slack.methods().groupsInvite(groupsInviteRequest);
            if (!groupsInviteResponse.isOk()){
                throw new DataConfigurationException(format( "Error adding user '%s' to team '%s'. Error was:%s" ,user.getEmail(),compositeName,groupsInviteResponse.getError()));
            }
        } catch (SlackApiException e) {
            throw new DataConfigurationException(format( "Error adding user '%s' to team '%s'" ,user.getEmail(),compositeName),e);
        }
    }

    private UsersLookupByEmailResponse getUserByEmail(User user) throws DataConfigurationException, IOException {
        UsersLookupByEmailRequest userLookup = UsersLookupByEmailRequest.builder().token(slackConfig.getOAuthKey()).email(user.getEmail()).build();
        try {
            return slack.methods().usersLookupByEmail(userLookup);
        }catch (SlackApiException e) {
            throw new DataConfigurationException(format( "Error fetching user for email: %s",user.getEmail()),e);
        }
    }

    private Group getGroup(String compositeName) throws IOException, SlackApiException, DataConfigurationException {
        GroupsListRequest groupsListRequest = GroupsListRequest.builder().token(slackConfig.getOAuthKey()).build();
        GroupsListResponse groupsListResponse = slack.methods().groupsList(groupsListRequest);
        Group group = groupsListResponse.getGroups().stream().filter(g -> g.getName().equals(compositeName)).findFirst().orElse(null);
        if(group ==null)
            throw new DataConfigurationException(format("There is no channel with a name of '%s'",compositeName));
        return group;
    }

    @Override
    public void removeUserFromTeam(String teamName, User user) throws IOException, DataConfigurationException, ActionNotRequiredException {

    }


    @PostConstruct
    public void configure() throws IOException {
        this.slackConfig = integrationServicesConfiguration.getKeyContent("slack-credentials.json", SlackConfig.class);
        slack = Slack.getInstance();
    }

}
