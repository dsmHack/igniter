package com.dsmhack.igniter;

import com.dsmhack.igniter.models.User;
import com.dsmhack.igniter.services.UserImportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.mockito.Mockito.*;

//@TestPropertySource("classpath:application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class TestBatchRunner {
    @Value("${keys.base.path:./}")
    private String keyPath;

    @Test
    public void testOnboardSlack_createsStuff(){
        UserImportService fakeUserImportService = mock(UserImportService.class);
        User aUser = new User("a", "b", "c", "d");

        when(fakeUserImportService.parseStringIntoUser(anyString())).thenReturn(aUser);

        BatchRunner batchRunner = new BatchRunner(fakeUserImportService);

        System.out.println("Key path: " + keyPath);

        batchRunner.onboardSlack();

    }
}
