package org.dampati.backend.controller;

import org.dampati.backend.entity.Interest;
import org.dampati.backend.service.InterestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.dampati.backend.dto.InterestResponseDTO;
import org.springframework.security.core.Authentication;


import java.util.List;

@RestController
@RequestMapping("/api/interests")
@CrossOrigin(origins = "http://localhost:5173")
public class InterestController {

    private InterestService interestService;

    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    @PostMapping
    public InterestResponseDTO sendInterest(
            Authentication authentication,
            @RequestParam Long receiverId) {
        return interestService.toResponseDTO(
                interestService.sendInterestByEmail(
                        authentication.getName(), receiverId)
        );
    }

    @GetMapping("/sent")
    public List<InterestResponseDTO> getInterests(
            Authentication authentication) {
        return interestService
                .getSentInterestByEmail(authentication.getName())
                .stream()
                .map(interestService::toResponseDTO)
                .toList();
    }

    @GetMapping("/received")
    public List<InterestResponseDTO> getReceivedInterests(
            Authentication authentication) {
        return interestService
                .getReceivedInterestsByEmail(authentication.getName())
                .stream()
                .map(interestService::toResponseDTO)
                .toList();
    }

    @PutMapping("/{interestId}/respond")
    public InterestResponseDTO respondToInterest(
            @PathVariable Long interestId,
            Authentication authentication,
            @RequestParam String status) {
        return interestService.toResponseDTO(
                interestService.respondToInterestByEmail(
                        interestId, authentication.getName(), status)
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(
            IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
}
