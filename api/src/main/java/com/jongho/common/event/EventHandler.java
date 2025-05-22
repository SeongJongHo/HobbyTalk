package com.jongho.common.event;

public interface EventHandler<T extends EventData> {

    void handle(Event<T> event);

    boolean supports(Event<T> event);
}
