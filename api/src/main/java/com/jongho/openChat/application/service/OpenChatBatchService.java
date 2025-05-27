package com.jongho.openChat.application.service;

import com.jongho.openChat.application.repository.IOpenChatBatchProcessingRunner;
import com.jongho.openChat.application.repository.IOpenChatBatchRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenChatBatchService {
     private final IOpenChatBatchRunner openChatBatchRunner;
     private final IOpenChatBatchProcessingRunner openChatBatchProcessingRunner;

    public void execute() {
        openChatBatchRunner.run();
    }

    public void process() {
        openChatBatchProcessingRunner.run();
    }
}
