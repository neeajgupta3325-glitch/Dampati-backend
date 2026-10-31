package org.dampati.backend.service;

import org.dampati.backend.dto.ChatListDTO;
import org.dampati.backend.entity.Message;
import org.dampati.backend.entity.User;
import org.dampati.backend.repository.MessageRepository;
import org.dampati.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(
            MessageRepository messageRepository,
            UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    public Message sendMessage(
            String senderEmail,
            Long receiverId,
            String content) {

        if (receiverId == null || receiverId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid receiver ID");
        }

        if (content == null || content.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Message cannot be empty");
        }

        if (content.length() > 2000) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Message cannot exceed 2000 characters");
        }

        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Receiver not found"));

        if (sender.getId().equals(receiver.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "You cannot message yourself");
        }

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);

        return messageRepository.save(message);
    }

    public List<Message> getConversation(
            String userEmail,
            Long receiverId) {

        if (receiverId == null || receiverId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid receiver ID");
        }

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found"));

       userRepository.findById(receiverId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Receiver not found"));

        if (currentUser.getId().equals(receiverId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Yor cannot message yourself");
        }

        return messageRepository.findConversation(
                currentUser.getId(), receiverId);
    }

    public List<ChatListDTO> getChatList(String userEmail) {

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found"));

        Long currentUserId = currentUser.getId();

        List<Message> messages =
                messageRepository.findUserMessages(currentUserId);

        Map<Long, ChatListDTO> chatMap = new LinkedHashMap<>();

       for (Message message : messages) {

           boolean sentByMe =
                   message.getSender().getId().equals(currentUserId);

           boolean receivedByMe =
                   message.getReceiver().getId().equals(currentUserId);

                    if (!sentByMe && !receivedByMe) {
                        continue;
                    }
                    User otherUser = sentByMe
                            ? message.getReceiver()
                            : message.getSender();
                    if (!chatMap.containsKey(otherUser.getId())) {
                        long unread = messageRepository.countUnread(
                                currentUserId, otherUser.getId());

                        chatMap.put(
                                otherUser.getId(),
                                new ChatListDTO(
                                        otherUser.getId(),
                                        otherUser.getName(),
                                        message.getContent(),
                                        unread,
                                        message.getSentAt()));
                    }
                }
        return new ArrayList<>(chatMap.values());
    }

    public void markAsSeen(String userEmail, Long partnerId) {

        if (partnerId == null || partnerId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid user ID");
        }

        User currentUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found"));

        messageRepository.markAsSeen(currentUser.getId(), partnerId);
    }
}
