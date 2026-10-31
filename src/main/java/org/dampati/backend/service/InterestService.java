package org.dampati.backend.service;


import org.dampati.backend.dto.InterestResponseDTO;
import org.dampati.backend.entity.Interest;
import org.dampati.backend.entity.User;
import org.dampati.backend.repository.InterestRepository;
import org.dampati.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestService {
    private final InterestRepository interestRepository;
    private final UserRepository userRepository;

    public InterestService(
            InterestRepository interestRepository,
            UserRepository userRepository) {
        this.interestRepository = interestRepository;
        this.userRepository = userRepository;
    }

    public Interest sendInterestByEmail(String senderEmail, Long receiverId) {
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        return sendInterest(sender.getId(), receiverId);
        }

    public List<Interest> getSentInterestByEmail(String senderEmail) {
        User sender = userRepository.findByEmail(senderEmail)
                .orElseThrow(()-> new IllegalArgumentException("Sender not found"));
        return getSentInterests(sender.getId());
    }

    public Interest respondToInterestByEmail(
            Long interestId, String receiverEmail, String status) {
        User receiver = userRepository.findByEmail(receiverEmail)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));
        return respondToInterest(interestId, receiver.getId(), status);
    }

    public List<Interest> getReceivedInterestsByEmail(String receiverEmail) {
        User receiver = userRepository.findByEmail(receiverEmail)
                .orElseThrow(()-> new IllegalArgumentException("Receiver not found"));
        return getReceivedInterests(receiver.getId());
    }

    public Interest sendInterest(Long senderId, Long receiverId) {
        if (interestRepository
                .findBySender_IdAndReceiver_Id(senderId, receiverId)
                .isPresent()) {
            throw new IllegalArgumentException(
                    "You have already sent interest to this  profile."
            );
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found."));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found."));

        Interest interest = new Interest();
        interest.setSender(sender);
        interest.setReceiver(receiver);

        return interestRepository.save(interest);
    }

    public List<Interest> getSentInterests(Long senderId) {
        return interestRepository.findBySender_Id(senderId);
    }

    public List<Interest> getReceivedInterests(Long receiverId) {
        return interestRepository.findByReceiver_Id(receiverId);
    }

    public Interest respondToInterest(
            Long interestId, Long receiverId, String status) {
    if (!status.equalsIgnoreCase("ACCEPTED")
            && !status.equalsIgnoreCase("DECLINED")) {
        throw new IllegalArgumentException(
                        "Status must be ACCEPTED or DECLINED.");
    }

            Interest interest = interestRepository.findById(interestId)
                    .orElseThrow(() -> new RuntimeException("Interest not found."));

            if (!interest.getReceiver().getId().equals(receiverId)) {
                throw new IllegalArgumentException(
                        "You are not authorized to respond to this interest."
                );
            }

            if (!"PENDING".equalsIgnoreCase(interest.getStatus())) {
                throw new IllegalArgumentException(
                        "This interest has already been responded to."
                );
            }

            interest.setStatus(status.toUpperCase());
            return interestRepository.save(interest);
    }

    public InterestResponseDTO toResponseDTO(Interest interest) {
        return new InterestResponseDTO(
                interest.getId(),
                interest.getSender().getName(),
                interest.getReceiver().getName(),
                interest.getStatus(),
                interest.getCreatedAt()
        );
    }
}

