package org.dampati.backend.controller;

import org.dampati.backend.dto.ChatListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.dampati.backend.entity.Message;
import org.dampati.backend.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")

public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService  = messageService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Message sendMessage(
            Authentication authentication,
            @RequestParam Long receiverId,
            @RequestParam String content) {

        return messageService.sendMessage(
                authentication.getName(),
                receiverId,
                content);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Message> getConversation(
            Authentication authentication,
            @RequestParam Long receiverId) {

        return messageService.getConversation(
                authentication.getName(),
                receiverId);
    }

    @GetMapping("/chats")
    public List<ChatListDTO> getChatList(
            Authentication authentication) {
        return messageService.getChatList(authentication.getName());
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/seen")
    public ResponseEntity<Void> marksSeen(
            Authentication authentication,
            @RequestParam Long partnerId) {
        messageService.markAsSeen(authentication.getName(), partnerId);
        return ResponseEntity.ok().build();
    }
}
