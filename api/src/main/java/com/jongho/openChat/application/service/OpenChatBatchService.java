package com.jongho.openChat.application.service;

import com.jongho.common.lock.redis.RedisKey;
import com.jongho.common.lock.service.LockService;
import com.jongho.openChat.application.repository.IOpenChatBatchGroupMessageRepository;
import com.jongho.openChat.application.repository.IOpenChatBatchGroupRepository;
import com.jongho.openChat.application.repository.IOpenChatBatchProcessingRunner;
import com.jongho.openChat.application.repository.IOpenChatBatchRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenChatBatchService {
     private final IOpenChatBatchRunner openChatBatchRunner;
     private final IOpenChatBatchProcessingRunner openChatBatchProcessingRunner;
    private final IOpenChatBatchGroupMessageRepository openChatBatchGroupMessageRepository;
    private final IOpenChatBatchGroupRepository openChatBatchGroupRepository;

    private final LockService lockService;

    public void execute() {
        if (lockService.acquireLock(RedisKey.BATCH) && isChatBatchRun()) {
            try {
                openChatBatchRunner.run();
            } finally {
                lockService.releaseLock(RedisKey.BATCH);
            }
        }
    }

    public void process() {
        openChatBatchProcessingRunner.run();
    }

    private boolean isChatBatchRun() {
        if (openChatBatchGroupRepository.hasKey() && openChatBatchGroupMessageRepository.hasKey()) {
            chatBatchRename();
            return true;
        }

        throw new IllegalStateException(
            "Chat batch is not running. Please check the batch configuration.");
    }

    private void chatBatchRename() {
        openChatBatchGroupRepository.rename();
        openChatBatchGroupMessageRepository.rename();
    }
}
