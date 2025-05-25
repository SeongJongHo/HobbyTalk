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
    public static String getChatRoomMessageKey(Long openChatRoomId) {
        return "chatRooms:" + openChatRoomId + ":chats";
    }
    public static String getChatMessageKey(Long openChatRoomId, Long chatId) {
        return "chatRooms:" + openChatRoomId + ":chats:" + chatId;
    }
    public static String getChatRoomProcessingKey(Long openChatRoomId) {
        return "chatGroups:" + openChatRoomId + ":processing";
    }
    public static String getChatGroupModKey(Long chatRoomId) {
        return "chatGroups:" + (chatRoomId % MOD);
    }
    public static String getChatGroupKey(Long groupId) {
        return "chatGroups:" + groupId;
    }
    public static String getChatGroupProcessingKey(Long groupId) {
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
