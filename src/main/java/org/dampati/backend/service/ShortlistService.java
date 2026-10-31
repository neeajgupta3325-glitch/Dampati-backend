package org.dampati.backend.service;

import org.dampati.backend.entity.Shortlist;
import org.dampati.backend.entity.User;
import org.dampati.backend.repository.ShortlistRepository;
import org.dampati.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortlistService {

    private final ShortlistRepository shortlistRepository;
    private final UserRepository userRepository;

    public ShortlistService(
            ShortlistRepository shortlistRepository,
            UserRepository userRepository) {
        this.shortlistRepository = shortlistRepository;
        this.userRepository = userRepository;
    }

    public Shortlist addToShortlist(Long userId, Long shortlistedUserId) {
        if (userId.equals(shortlistedUserId)) {
            throw new IllegalArgumentException("You cannot shortlist your own profile");
        }

        if (shortlistRepository
                .findByUser_IdAndShortlistedUser_Id(userId, shortlistedUserId)
                .isPresent()) {
            throw new IllegalArgumentException("Profile is already shortlisted");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User shortlistedUser = userRepository.findById(shortlistedUserId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        Shortlist shortlist = new Shortlist();
        shortlist.setUser(user);
        shortlist.setShortlistedUser(shortlistedUser);

        return shortlistRepository.save(shortlist);
    }

    public List<Shortlist> getShortlist(Long userId) {
        return shortlistRepository.findByUser_Id(userId);

    }
        public void removeFromShortlist(Long id, Long userId) {
            Shortlist shortlist = shortlistRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Shortlist entry not found"));


            if (!shortlist.getUser().getId().equals(userId)) {
                throw new IllegalArgumentException("You cannot remove another user's shortlist entry");
            }
            shortlistRepository.delete(shortlist);
        }
}
