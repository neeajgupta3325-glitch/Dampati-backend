package org.dampati.backend.repository;

import org.dampati.backend.entity.PartnerPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerPreferenceRepository extends JpaRepository<PartnerPreference, Long> {
    Optional<PartnerPreference> findByUser_Id(Long userId);
}
