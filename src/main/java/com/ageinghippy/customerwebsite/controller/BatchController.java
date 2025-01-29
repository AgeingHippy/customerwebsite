package com.ageinghippy.customerwebsite.controller;


import com.ageinghippy.customerwebsite.service.BatchJobService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job customerLoadJob;

    @Autowired
    private BatchJobService batchJobService;

    @GetMapping("/jobs")
    public String jobExecutions(Model model) {

        model.addAttribute("jobExecutions", batchJobService.getJobExecutions());

        return "/batch";
    }

    @PostMapping("/runJob")
    public String runJob(Model model, @RequestParam String fileName) {
        model.addAttribute("jobExecutions",
                List.of(batchJobService.runCustomerLoadJob(fileName)));
        return "/batch";
    }
}

