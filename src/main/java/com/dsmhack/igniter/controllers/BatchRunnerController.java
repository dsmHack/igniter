package com.dsmhack.igniter.controllers;

import com.dsmhack.igniter.BatchRunner;
import com.dsmhack.igniter.services.user.UserImportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Controller
public class BatchRunnerController {

    private final BatchRunner batchRunner;

    @Autowired
    public BatchRunnerController(BatchRunner batchRunner) {
        this.batchRunner = batchRunner;
    }

    @RequestMapping("api/runBatch")
    public String runBatchWithFile(@RequestParam("filename") String filename) throws UserImportException, FileNotFoundException {
        batchRunner.onboardEveryone(new FileReader(filename));
        return "success";
    }


}
