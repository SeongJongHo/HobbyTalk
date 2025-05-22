package com.jongho.common.event.data;


import com.jongho.common.event.EventData;

public record ChatBatchData(String chatRoomId) implements EventData {

}
