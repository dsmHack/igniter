package com.dsmhack.igniter;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.UserImportService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
public class BatchRunner {
    private UserImportService userImportService;

    public BatchRunner() {}

    @Autowired
    public BatchRunner(UserImportService userImportService) {
        this.userImportService = userImportService;
    }


    String team = "whatever~";

    public void onboardSlack(){
        System.out.println("team: " + team);
        User user = this.userImportService.parseStringIntoUser("idk");

    }

}
