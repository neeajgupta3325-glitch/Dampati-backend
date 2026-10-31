package org.dampati.backend.dto;

import java.time.LocalDateTime;

public class ChatListDTO {

    private Long userId;
    private String name;
    private String lastMessage;
    private long unreadCount;
    private java.time.LocalDateTime lastMessageDate;

    public LocalDateTime getLastMessageDate() {
        return lastMessageDate;
    }

    public ChatListDTO(Long userId, String name, String lastMessage, long unreadCount, java.time.LocalDateTime lastMessageDate) {
      this.userId = userId;
      this.name = name;
      this.lastMessage = lastMessage;
      this.unreadCount = unreadCount;
      this.lastMessageDate = lastMessageDate;

    }

    public long getUnreadCount() {
        return unreadCount;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
