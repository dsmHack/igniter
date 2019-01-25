package com.dsmhack.igniter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestBatchRunner extends SpringContextConfiguration {

    private static final String USER_FILE_PATH = "src/test/resources/exampleUser.csv";

    @Autowired
    BatchRunner batchRunner;

    @Test
    public void testOnboardSlack_createsStuff(){

        System.out.println("runner: " + batchRunner);

        batchRunner.onboardEveryone(USER_FILE_PATH);

    }
}
