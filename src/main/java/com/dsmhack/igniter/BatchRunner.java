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
    private final UserImportService userImportService;
    private final TeamConfigurationService teamConfigurationService;

    @Autowired
    public BatchRunner(TeamConfigurationService teamConfigurationService,UserImportService userImportService) {
        this.teamConfigurationService = teamConfigurationService;
        this.userImportService = userImportService;
    }

    public void onboardEveryone() {

    }

}
