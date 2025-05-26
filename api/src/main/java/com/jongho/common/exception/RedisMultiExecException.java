package com.jongho.common.exception;

import org.springframework.http.HttpStatus;

public class RedisMultiExecException extends CustomBusinessException{
    public RedisMultiExecException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}