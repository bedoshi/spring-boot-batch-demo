package com.example.demo.batch;

import com.example.demo.service.TimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBatchTest
@SpringBootTest(classes = { SimpleBatchConfig.class })
public class SimpleMessageTaskletTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @SpyBean
    private TimeService timeService;

    @Test
    public void testJob() throws Exception {
        // action
        JobExecution jobExecution = this.jobLauncherTestUtils.launchJob(
                new JobParametersBuilder()
                        .addString("message", "test")
                        .toJobParameters());
        // verify
        assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
        verify(timeService, times(3)).getCurrentTime(); // 3steps. And it calls once per a step.
    }
}
