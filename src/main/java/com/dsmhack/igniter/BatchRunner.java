package com.dsmhack.igniter;

import com.dsmhack.igniter.services.TeamConfigurationService;
import com.dsmhack.igniter.services.UserImportService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class BatchRunner {
    private UserImportService userImportService;
    private TeamConfigurationService teamConfigurationService;

    public BatchRunner() {}

    @Autowired
    public BatchRunner(UserImportService userImportService, TeamConfigurationService teamConfigurationService) {
        this.userImportService = userImportService;
        this.teamConfigurationService = teamConfigurationService;
    }

    public void onboardEveryone() {

    }

}
