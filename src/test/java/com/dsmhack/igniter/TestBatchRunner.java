package com.dsmhack.igniter;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.TeamConfigurationService;
import com.dsmhack.igniter.services.UserImportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestBatchRunner extends SpringContextConfiguration {
    @Autowired
    private UserImportService userImportService;
    @Autowired
    private TeamConfigurationService teamConfigurationService;
    @Autowired
    BatchRunner batchRunner;

    @Test
    public void testOnboardSlack_createsStuff(){
//        UserImportService fakeUserImportService = mock(UserImportService.class);
        User aUser = new User("a", "b", "c", "d");
//        when(fakeUserImportService.parseStringIntoUser(anyString())).thenReturn(aUser);
//        BatchRunner batchRunner = new BatchRunner(fakeUserImportService, teamConfigurationService);
//        if(this.teamConfigurationService != null) {
//        }
        //aseertEquals("idk", batchRunner, instanceOf(BatchRunner.class));
        System.out.println("runner: " + batchRunner);

    }
}
