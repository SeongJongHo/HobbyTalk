package com.jongho.openChat.application.scheduler;

import com.jongho.common.lock.redis.RedisKey;
import com.jongho.common.lock.service.LockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class ChatBatchScheduler {
    private final JobLauncher jobLauncher;
    private final Job chatMessageJob;
    private final LockService lockService;

    @Scheduled(fixedRate = 300000)
    public void execute() {
        if(lockService.acquireLock(RedisKey.BATCH)){
            try {
                runBatchJob();
            } catch (Exception e) {
                log.error(e.getMessage());
            } finally {
                lockService.releaseLock(RedisKey.BATCH);
            }
        }
    }

    public void runBatchJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(chatMessageJob, new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters());
    }
}
