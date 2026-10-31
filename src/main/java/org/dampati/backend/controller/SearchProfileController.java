package org.dampati.backend.controller;

import org.dampati.backend.dto.SearchProfileDTO;
import org.dampati.backend.service.SearchProfileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@CrossOrigin(origins = "http://localhost:5173")
public class SearchProfileController {
    private final SearchProfileService searchProfileService;

    public SearchProfileController(
            SearchProfileService searchProfileService) {
        this.searchProfileService  = searchProfileService;
    }

    @GetMapping
    public List<SearchProfileDTO> getAllProfiles() {
        return searchProfileService.getAllProfiles();
    }

}
