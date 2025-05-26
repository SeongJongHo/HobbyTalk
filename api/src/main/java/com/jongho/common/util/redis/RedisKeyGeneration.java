package com.jongho.common.util.redis;

public class RedisKeyGeneration {
    public static int MOD = 100;
    public static String getChatRoomConnectionInfoKey(Long userId, Long openChatRoomId) {
        return "users:" + userId + ":chatRooms:" + openChatRoomId + ":connectionInfo";
    }
    public static String getJoinedChatRoomsKey(Long userId) {
        return "users:" + userId + ":chatRooms";
    }
    public static String getChatRoomKey(Long openChatRoomId) {
        return "chatRooms:" + openChatRoomId;
    }

    /**
     * 채팅 캐시큐 키 생성
     * @param openChatRoomId 오픈 채팅방 ID
     * @return 채팅방 메시지 키
     */
    public static String getChatRoomMessageKey(Long openChatRoomId) {
        return "chatRooms:" + openChatRoomId + ":chats";
    }

    /**
     * 단일 채팅 메시지 키 생성
     * @param openChatRoomId 오픈 채팅방 ID
     * @param chatId 채팅 ID
     * @return 채팅 메시지 키
     */
    public static String getChatMessageKey(Long openChatRoomId, Long chatId) {
        return "chatRooms:" + openChatRoomId + ":chats:" + chatId;
    }

    /**
     * 채팅 배치큐 키 생성
     * @param openChatRoomId 오픈 채팅방 ID
     * @return 채팅 배치큐 키
     */
    public static String getChatBatchKey(Long openChatRoomId) {
        return "chatGroups:" + openChatRoomId + ":batch";
    }
    public static String getChatBatchProcessingKey(Long openChatRoomId) {
        return "chatGroups:" + openChatRoomId + ":batch:processing";
    }
    public static String getChatGroupKey() {
        return "chatGroups";
    }
    public static String getChatGroupProcessingKey() {
        return "chatGroups:processing";
    }
    public static String getChatGroupModProcessingKey(Long groupId) {
        return "chatGroups:" + groupId + ":processing";
    }
    public static String getLastMessageKey(Long openChatRoomId) {
        return "chatRooms:" + openChatRoomId + ":lastMessage";
    }
    public static String getChatRoomUserListKey(Long openChatRoomId) {
        return "chatRooms:" + openChatRoomId + ":users";
    }
    public static String getUserProfileKey(Long userId) {
        return "users:" + userId + ":profile";
    }
}
