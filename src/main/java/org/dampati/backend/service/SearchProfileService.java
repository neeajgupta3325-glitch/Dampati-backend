package org.dampati.backend.service;

import org.dampati.backend.dto.SearchProfileDTO;
import org.dampati.backend.repository.SearchProfileRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class SearchProfileService {

    private final SearchProfileRepository searchProfileRepository;

    public SearchProfileService(
            SearchProfileRepository searchProfileRepository
    ){
        this.searchProfileRepository = searchProfileRepository;
    }

    public List<SearchProfileDTO> getAllProfiles() {
        return searchProfileRepository.findAll()
                .stream()
                .map(user -> new SearchProfileDTO(
                      user.getId(),
                      user.getName(),
                      user.getGender(),
                      user.getDateOfBirth() == null
                            ? null
                            :user.getDateOfBirth().toString(),
                      user.getReligion(),
                      user.getMotherTongue(),
                      user.getLocation(),
                      user.getEducation(),
                      user.getOccupation()
                ))
                .toList();
    }
}
