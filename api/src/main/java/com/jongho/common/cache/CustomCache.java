package com.jongho.common.cache;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jongho.common.util.serializer.DataSerializer;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomCache {

    private String data;
    private LocalDateTime expiredAt;

    public static CustomCache of(Object data, Duration ttl) {
        return new CustomCache(DataSerializer.serialize(data),
            LocalDateTime.now().plusSeconds(ttl.getSeconds()));
    }

    @JsonIgnore
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }

    public <T> T parseData(Type dataType) {
        return DataSerializer.deserialize(data, dataType);
    }
}
