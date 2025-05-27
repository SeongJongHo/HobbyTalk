package com.jongho.openChat.common.config;

import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing(dataSourceRef = "sourceDataSource")
public class OpenChatBatchConfig {
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor tx = new ThreadPoolTaskExecutor();
        tx.setCorePoolSize(8);
        tx.setMaxPoolSize(48);
        tx.setQueueCapacity(100);
        tx.setThreadNamePrefix("chat-batch-worker-");
        tx.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        tx.afterPropertiesSet();
        return tx;
    }
}