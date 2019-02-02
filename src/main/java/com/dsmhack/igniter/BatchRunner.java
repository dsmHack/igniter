package com.dsmhack.igniter;

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
    private final IgniterProperties igniterProperties;
    private final TeamConfigurationService teamConfigurationService;

    @Autowired
    public BatchRunner(TeamConfigurationService teamConfigurationService, UserImportService userImportService, IgniterProperties igniterProperties) {
        this.teamConfigurationService = teamConfigurationService;
        this.userImportService = userImportService;
        this.igniterProperties = igniterProperties;
    }

    public void onboardEveryone(String fileName) throws UserImportException {
        System.out.println("onboard.filename: " + fileName);
        List<User> usersByList = userImportService.getUsers(fileName);
        for (int i = 1; i <= igniterProperties.getTeamNumber(); i++) {
            String compositeName = igniterProperties.getCompositeName(i);
            System.out.println("compositeName: " + compositeName);
            teamConfigurationService.createTeam(compositeName);
            usersByList.forEach(user ->{ teamConfigurationService.addUserToTeam(compositeName, user); } );
        }
    }
}
