package org.dampati.backend.controller;

import org.dampati.backend.entity.PartnerPreference;
import org.dampati.backend.service.PartnerPreferenceService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/preferences")
@CrossOrigin(origins = "http://localhost:5173")
public class PartnerPreferenceController {

    private final PartnerPreferenceService partnerPreferenceService;

    public PartnerPreferenceController(
            PartnerPreferenceService partnerPreferenceService) {
        this.partnerPreferenceService = partnerPreferenceService;
    }

    @PostMapping
    public PartnerPreference createPreference(
            @RequestParam Long userId,
            @RequestBody PartnerPreference preference) {
        return partnerPreferenceService.savePreference(
                userId,
                preference);
    }

    @GetMapping("/{id}")
    public PartnerPreference getPreference(@PathVariable Long id) {
        return partnerPreferenceService
                .getPreferenceById(id)
                .orElse(null);
    }

    @PutMapping("/{id}")
    public PartnerPreference updatePreference(
            @PathVariable Long id,
            @RequestBody PartnerPreference updatedPreference) {

        return partnerPreferenceService.updatePreference(
                id,
                updatedPreference
        );
    }

    @GetMapping("/user/{userId}")
    public PartnerPreference getPreferenceByUserId(
            @PathVariable Long userId) {
        return partnerPreferenceService
                .getPreferenceByUserId(userId)
                .orElse(null);
    }
}
