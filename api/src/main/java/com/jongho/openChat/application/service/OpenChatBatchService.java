package com.jongho.openChat.application.service;

import com.jongho.openChat.application.repository.IOpenChatBatchRunner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenChatBatchService {
     private final IOpenChatBatchRunner openChatBatchRunner;

    public void execute() {
        openChatBatchRunner.run();
    }
}
