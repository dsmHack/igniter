package com.dsmhack.igniter;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.UserImportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestBatchRunner {
    @Value("${keys.base.path:./}")
    private String keyPath;

    @Mock
    UserImportService fakeUserImportService;

    @Test
    public void testOnboardSlack_createsStuff(){
//        UserImportService fakeUserImportService = mock(UserImportService.class);
        User aUser = new User("a", "b", "c", "d");


        when(fakeUserImportService.parseStringIntoUser(anyString())).thenReturn(aUser);

        BatchRunner batchRunner = new BatchRunner(fakeUserImportService);

        System.out.println("Key path: " + keyPath);

        batchRunner.onboardSlack();

    }
}
