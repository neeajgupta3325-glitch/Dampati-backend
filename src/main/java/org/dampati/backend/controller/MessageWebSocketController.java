
package org.dampati.backend.controller;

import org.dampati.backend.dto.ChatMessageDTO;
import org.dampati.backend.entity.Message;
import org.dampati.backend.entity.User;
import org.dampati.backend.repository.UserRepository;
import org.dampati.backend.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
public class MessageWebSocketController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;
    private final SimpUserRegistry userRegistry;

    public MessageWebSocketController(
            MessageService messageService,
            SimpMessagingTemplate messagingTemplate,
            UserRepository userRepository,
            SimpUserRegistry userRegistry) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
        this.userRepository = userRepository;
        this.userRegistry = userRegistry;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(
            @Payload ChatMessageDTO request,
            Authentication authentication) {

        if (authentication == null || authentication.getName() == null) {
            throw new IllegalArgumentException("Unauthorized");
        }

        User sender = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        Message saved = messageService.sendMessage(
                sender.getEmail(),
                request.getReceiverId(),
                request.getContent());

        ChatMessageDTO response = new ChatMessageDTO(
                saved.getId(),
                saved.getSender().getId(),
                saved.getSender().getName(),
                saved.getReceiver().getId(),
                saved.getContent(),
                saved.getSentAt());

        System.out.println("RECEIVER PRINCIPAL: " +
                saved.getReceiver().getEmail());

        System.out.println("SENDER PRINCIPAL: " +
                saved.getSender().getEmail());

        System.out.println("CONNECTED WS USERS:");
        userRegistry.getUsers().forEach(user ->
                System.out.println("WS USER: " + user.getName())
        );

        // Send the saved message to the receiver
        messagingTemplate.convertAndSendToUser(
                saved.getReceiver().getEmail(),
                "/queue/messages",
                response
        );

// Send the saved message to the sender
        messagingTemplate.convertAndSendToUser(
                saved.getSender().getEmail(),
                "/queue/messages",
                response
        );
        System.out.println("SENDER EMAIL: " + saved.getSender().getEmail());
        System.out.println("RECEIVER EMAIL: " + saved.getReceiver().getEmail());
        System.out.println("MESSAGE SAVED: " + saved.getId());
    }
}
