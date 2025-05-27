package com.jongho.openChat.infra.job;

import com.jongho.openChat.application.repository.IOpenChatBatchRunner;
import com.jongho.openChat.domain.model.OpenChat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenChatBatchRunner implements IOpenChatBatchRunner {
    private final OpenChatBatchReader openChatBatchReader;
    private final OpenChatBatchWriter openChatBatchWriter;
    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final TaskExecutor taskExecutor;

    @Override
    public void run() {
        try {
            jobLauncher.run(chatBatchJob(), new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters());
        } catch (Exception e) {
            log.error("Failed to run chat batch job e.message: {}", e.getMessage());
            throw new RuntimeException("Failed to run chat batch job", e);
        }
    }

    public Job chatBatchJob() {
        return new JobBuilder("chatBatchJob", jobRepository)
            .start(chatBatchStep())
            .build();
    }

    private Step chatBatchStep() {
        return new StepBuilder("chatBatchStep", jobRepository)
            .<List<String>, List<String>>chunk(1, transactionManager)
            .reader(openChatBatchReader)
            .writer(openChatBatchWriter)
            .taskExecutor(taskExecutor)
            .build();
    }
}
