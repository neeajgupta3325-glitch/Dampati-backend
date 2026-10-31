package org.dampati.backend.repository;

import org.dampati.backend.entity.Shortlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShortlistRepository
    extends JpaRepository<Shortlist, Long> {

        List<Shortlist> findByUser_Id(Long userId);

        Optional<Shortlist> findByUser_IdAndShortlistedUser_Id(
                Long userId, Long shortlistedUserId);
    }

