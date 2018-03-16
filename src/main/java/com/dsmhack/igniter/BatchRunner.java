package com.dsmhack.igniter;

import com.dsmhack.igniter.services.UserImportService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class BatchRunner {
    private UserImportService userImportService;

    public BatchRunner() {}

    public BatchRunner(UserImportService userImportService) {
        this.userImportService = userImportService;
    }

    @Value("${team.prefix:''}")
    String team;

    public void onboardSlack(){
        System.out.println("team: " + team);
        this.userImportService.parseStringIntoUser("idk");
    }

}
