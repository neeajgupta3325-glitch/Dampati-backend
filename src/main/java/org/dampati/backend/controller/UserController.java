package org.dampati.backend.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.dampati.backend.dto.UserResponseDTO;
import org.dampati.backend.service.PasswordResetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.dampati.backend.entity.User;

import org.dampati.backend.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.dampati.backend.security.JwtService;
import org.dampati.backend.dto.SearchProfileDTO;
import org.dampati.backend.service.SearchProfileService;
import java.util.Map;




import java.util.List;



@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final SearchProfileService searchProfileService;
    private final PasswordResetService passwordResetService;

    public UserController(
            UserService userService,
            JwtService jwtService,
            SearchProfileService searchProfileService,
            PasswordResetService passwordResetService)
    {

        this.userService = userService;
        this.jwtService = jwtService;
        this.searchProfileService = searchProfileService;
        this.passwordResetService  = passwordResetService;

    }

    @PostMapping
    public UserResponseDTO  createUser(@Valid @RequestBody User user) {
        User savedUser = userService.saveUser(user);

        return new UserResponseDTO(
            savedUser.getId(),
            savedUser.getName(),
            savedUser.getEmail(),
            savedUser.getMobile(),
            savedUser.getGender(),
            savedUser.getDateOfBirth(),
            savedUser.getReligion(),
            savedUser.getMotherTongue(),
            savedUser.getLocation(),
            savedUser.getEducation(),
            savedUser.getOccupation()

        );
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {

        User loggedInUser = userService.loginUser(
                user.getEmail(),
                user.getPassword()
        );
        if (loggedInUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                    "message", "Invalid email or password"
                    ));
        }
        return ResponseEntity.ok(
                Map.of(
                "token", jwtService.generateToken(loggedInUser.getEmail()),
                "user", new UserResponseDTO(
                        loggedInUser.getId(),
                        loggedInUser.getName(),
                        loggedInUser.getEmail(),
                        loggedInUser.getMobile(),
                        loggedInUser.getGender(),
                        loggedInUser.getDateOfBirth(),
                        loggedInUser.getReligion(),
                        loggedInUser.getMotherTongue(),
                        loggedInUser.getLocation(),
                        loggedInUser.getEducation(),
                        loggedInUser.getOccupation()
                        )
                )
        );
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestBody Map<String, String> data) {
        String email = data.get("email");

        if (email == null || email.isBlank()) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.BAD_REQUEST,
                    "Email is required"
            );
        }
        String otp = String.format("%06d",
                new java.security.SecureRandom().nextInt(1_000_000));

        passwordResetService.sendOtpEmail(email, otp);

        return "OTP sent successfully";
    }

    @GetMapping("/{id}")
    public User getUserById(
            @PathVariable Long id) {
        User requestedUser = userService.getUserById(id).orElse(null);

        if (requestedUser == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND,
                    "User not found"
            );
        }

        return requestedUser;
    }

    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser,
            Authentication authentication
    ) {
        User existingUser = userService.getUserById(id).orElse(null);
        if (existingUser == null ||
                !existingUser.getEmail().equals(authentication.getName())) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.FORBIDDEN);
        }
            return userService.updateUser(id, updatedUser);

    }

    @PutMapping("/reset-password")
    @Transactional
    public ResponseEntity<String> resetPassword(
        @RequestBody Map<String, String> data) {

        String email = data.get("email");
        String newPassword = data.get("password");

        if (email == null || email.isBlank()
            || newPassword == null || newPassword.isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Email or password is required");
        }

        User existingUser = userService.findByEmail(email);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (!passwordResetService.consumeVerification(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Verify OTP before resetting password");
        }

        userService.resetPassword(existingUser.getId(), newPassword);

            return ResponseEntity.ok("Password reset successfully");
        }

    @GetMapping
    public List<SearchProfileDTO> getAllProfiles() {
        return searchProfileService.getAllProfiles();
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String otp = data.get("otp");

        if (email == null || otp == null) {
            return ResponseEntity.badRequest().body("Email and OTP are required");
        }

        boolean verified = passwordResetService.verifyOtp(email, otp);

        if (!verified) {
            return ResponseEntity.badRequest().body("Invalid or expired OTP");
        }

        return ResponseEntity.ok("OTP verified successfully");
    }
}