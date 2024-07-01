package com.example.demo.batch;

import com.example.demo.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

public class SimpleMessageTasklet implements Tasklet {

    TimeService timeService;

    SimpleMessageTasklet(TimeService timeService) {
        this.timeService = timeService;
    }
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String message = chunkContext.getStepContext().getJobParameters().get("message").toString();
        System.out.println("Message: " + message + ", it's " + timeService.getCurrentTime() + " now.");
        return RepeatStatus.FINISHED;
    }
}
