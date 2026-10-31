package org.dampati.backend.controller;

import org.dampati.backend.entity.Shortlist;
import org.dampati.backend.service.ShortlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shortlist")
@CrossOrigin(origins = "http://localhost:5173")
public class ShortlistController {

    private final ShortlistService shortlistService;

    public ShortlistController(ShortlistService shortlistService) {
        this.shortlistService = shortlistService;
    }

    @PostMapping
    public Shortlist addToShortlist(
            @RequestParam Long userId,
            @RequestParam Long shortlistedUserId) {
        return shortlistService.addToShortlist(userId, shortlistedUserId);
    }

    @GetMapping
    public List<Shortlist> getShortlist(@RequestParam Long userId) {
        return shortlistService.getShortlist(userId);
    }

    @DeleteMapping("/{id}")
    public void removeFromShortlist(
            @PathVariable Long id,
            @RequestParam Long userId) {
        shortlistService.removeFromShortlist(id, userId);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(
            IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
}
