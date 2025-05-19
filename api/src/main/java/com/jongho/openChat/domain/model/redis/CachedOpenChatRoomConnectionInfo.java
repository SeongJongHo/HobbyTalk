package com.jongho.openChat.domain.model.redis;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CachedOpenChatRoomConnectionInfo {
    private int active;
    private int unReadMessageCount;
    private String lastExitTime;

    @JsonCreator
    public CachedOpenChatRoomConnectionInfo(
            @JsonProperty("active") int active,
            @JsonProperty("unReadMessageCount") int unReadMessageCount,
            @JsonProperty("lastExitTime") String lastExitTime) {
        this.active = active;
        this.unReadMessageCount = unReadMessageCount;
        this.lastExitTime = lastExitTime;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("active", this.active+"");
        map.put("unReadMessageCount", this.unReadMessageCount+"");
        map.put("lastExitTime", this.lastExitTime+"");

        return map;
    }
}
