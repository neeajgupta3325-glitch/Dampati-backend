package org.dampati.backend.service;

import org.dampati.backend.entity.PartnerPreference;
import org.dampati.backend.entity.User;
import org.dampati.backend.repository.PartnerPreferenceRepository;
import org.dampati.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class PartnerPreferenceService {
    private final PartnerPreferenceRepository partnerPreferenceRepository;
    private final UserRepository userRepository;

    public PartnerPreferenceService(
            PartnerPreferenceRepository partnerPreferenceRepository,
            UserRepository userRepository) {
        this.partnerPreferenceRepository = partnerPreferenceRepository;
        this.userRepository = userRepository;
    }

    public PartnerPreference savePreference(
            Long userId,
            PartnerPreference preference) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        preference.setUser(user);

        return partnerPreferenceRepository.save(preference);
    }

    public Optional<PartnerPreference> getPreferenceByUserId(Long userId) {
        return partnerPreferenceRepository.findByUser_Id(userId);
    }

    public Optional<PartnerPreference> getPreferenceById(Long id) {
        return partnerPreferenceRepository.findById(id);
    }

    public PartnerPreference updatePreference(Long id, PartnerPreference updatedPreference) {

        Optional<PartnerPreference> existingPreference =
                partnerPreferenceRepository.findById(id);

        if (existingPreference.isPresent()) {
            PartnerPreference preference = existingPreference.get();

            preference.setPreferredGender(updatedPreference.getPreferredGender());
            preference.setPreferredReligion(updatedPreference.getPreferredReligion());
            preference.setPreferredMotherTongue(updatedPreference.getPreferredMotherTongue());
            preference.setPreferredLocation(updatedPreference.getPreferredLocation());
            preference.setPreferredEducation(updatedPreference.getPreferredEducation());
            preference.setPreferredOccupation(updatedPreference.getPreferredOccupation());
            preference.setMinAge(updatedPreference.getMinAge());
            preference.setMaxAge(updatedPreference.getMaxAge());

            return partnerPreferenceRepository.save(preference);
        }
        return null;
    }

}
