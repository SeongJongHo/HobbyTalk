package com.jongho.common.event;

import com.jongho.common.util.serializer.DataSerializer;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Event<T extends EventData> {

    private final EventType type;
    private final T data;

    @Builder
    private Event(EventType type, T data) {
        this.type = type;
        this.data = data;
    }

    public static Event<EventData> fromJson(String json) {
        EventRaw eventRaw = DataSerializer.deserialize(json, EventRaw.class);
        if (eventRaw == null) {
            return null;
        }
        return Event.<EventData>builder()
            .type(EventType.from(eventRaw.getType()))
            .data(DataSerializer.deserialize(eventRaw.getData(),
                Objects.requireNonNull(EventType.from(eventRaw.getType())).getDataClass()))
            .build();
    }

    public String toJson() {
        return DataSerializer.serialize(this);
    }

    @Getter
    private static class EventRaw {

        private String type;
        private Object data;
    }
}
