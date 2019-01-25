package com.dsmhack.igniter;

import com.dsmhack.igniter.configuration.IntegrationServicesConfiguration;
import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.TeamConfigurationService;
import com.dsmhack.igniter.services.user.UserImportException;
import com.dsmhack.igniter.services.user.UserImportService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@Service
public class BatchRunner {

    private final UserImportService userImportService;
    private final IntegrationServicesConfiguration integrationServicesConfiguration;
    private final TeamConfigurationService teamConfigurationService;

    @Autowired
    public BatchRunner(TeamConfigurationService teamConfigurationService, UserImportService userImportService, IntegrationServicesConfiguration integrationServicesConfiguration) {
        this.teamConfigurationService = teamConfigurationService;
        this.userImportService = userImportService;
        this.integrationServicesConfiguration = integrationServicesConfiguration;
    }

    public void onboardEveryone(String fileName) throws UserImportException {
        System.out.println("onboard.filename: " + fileName);
        List<User> usersByList = userImportService.getUsers(fileName);
        for (Integer i = 1; i <= integrationServicesConfiguration.getTeamNumber(); i++) {
            String compositeName = integrationServicesConfiguration.getCompositeName(i.toString());
            System.out.println("compositeName: " + compositeName);
            teamConfigurationService.createTeam(compositeName);
            usersByList.forEach(user ->{ teamConfigurationService.addUserToTeam(compositeName, user); } );
        }
    }
}
