package com.jongho.openChat.presentation.scheduler;

import com.jongho.openChat.application.service.OpenChatBatchService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatBatchScheduler {
    private final OpenChatBatchService openChatBatchService;

    @Scheduled(fixedRate = 300000)
    public void execute() {
        Duration startTime = Duration.ofMillis(System.currentTimeMillis());
        log.info("OpenChat Batch Scheduler started");
        try {
            openChatBatchService.execute();
            log.info("OpenChat Batch Scheduler completed successfully");
        } catch (Exception e) {
            log.error("Error occurred during OpenChat Batch execution", e);
        }

        Duration endTime = Duration.ofMillis(System.currentTimeMillis());
        log.info("OpenChat Batch Scheduler execution time: {} ms", endTime.minus(startTime).toMillis());
    }
}
