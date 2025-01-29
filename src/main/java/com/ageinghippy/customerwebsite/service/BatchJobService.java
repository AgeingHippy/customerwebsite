package com.ageinghippy.customerwebsite.service;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BatchJobService {
    private final JobExplorer jobExplorer;

    private final JobLauncher jobLauncher;

    private final Job customerLoadJob;

    public List<JobExecution> getJobExecutions() {
        return jobExplorer.getJobExecutions(Objects.requireNonNull(jobExplorer.getLastJobInstance("customer-loader")));
    }

    public JobExecution runCustomerLoadJob(String filePath) {

        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        jobParametersBuilder.addString("filePath", filePath);

        JobExecution jobExecution;
        try {
            jobExecution =
                    jobLauncher.run(
                            customerLoadJob,
                            jobParametersBuilder.toJobParameters()
                    );
        } catch (JobExecutionAlreadyRunningException
                 | JobRestartException
                 | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException e) {
            e.printStackTrace();
            // return exception message
            throw new RuntimeException(e);
            //todo - add some valid handling here
        }
        // return job execution status
        return jobExecution;
    }
}
