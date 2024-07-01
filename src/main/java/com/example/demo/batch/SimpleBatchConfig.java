package com.example.demo.batch;

import com.example.demo.service.TimeService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@ComponentScan("com.example.demo")
public class SimpleBatchConfig {

    @Autowired
    private TimeService timeService;

    @Bean
    public Job messageJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        System.out.println("messageJob メソッドを実行");
        return new JobBuilder("messageJob", jobRepository)
                .start(helloStep(jobRepository, platformTransactionManager))
                .next(worldStep(jobRepository, platformTransactionManager))
                .next(timeStep(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step helloStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        System.out.println("helloStep メソッドを実行");
        return new StepBuilder("myHelloStep", jobRepository)
                .tasklet(new SimpleMessageTasklet(timeService), platformTransactionManager)
                .build();
    }

    @Bean
    public Step worldStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        System.out.println("worldStep メソッドを実行");
        return new StepBuilder("myWorldStep", jobRepository)
                .tasklet(new SimpleMessageTasklet(timeService), platformTransactionManager)
                .build();
    }

    @Bean
    public Step timeStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        System.out.println("timeStep メソッドを実行");
        return new StepBuilder("myTimeStep", jobRepository)
                .tasklet(new SimpleMessageTasklet(timeService), platformTransactionManager)
                .build();
    }
}
