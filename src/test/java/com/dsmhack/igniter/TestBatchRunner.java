package com.dsmhack.igniter;

import com.dsmhack.igniter.services.user.UserImportException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestBatchRunner extends SpringContextConfiguration {

    private static final String USER_FILE_PATH = "src/test/resources/exampleUser.csv";

    @Autowired
    BatchRunner batchRunner;

    @Test
    @Ignore
    public void testOnboardSlack_createsStuff() throws UserImportException, FileNotFoundException {

        System.out.println("runner: " + batchRunner);

        batchRunner.onboardEveryone(new FileReader(USER_FILE_PATH));

    }
}
