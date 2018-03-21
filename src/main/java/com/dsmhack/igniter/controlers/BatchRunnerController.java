package com.dsmhack.igniter.controlers;

import com.dsmhack.igniter.BatchRunner;
import com.dsmhack.igniter.models.TeamValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationPid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class BatchRunnerController {

    private final BatchRunner batchRunner;

    @Autowired
    public BatchRunnerController(BatchRunner batchRunner) {
        this.batchRunner = batchRunner;
    }



    @RequestMapping("api/runBatch/{filename}")
    public String runBatchWithFile(@PathVariable("filename") String filename){
        batchRunner.onboardEveryone(filename);
        return "success";
    }


}
