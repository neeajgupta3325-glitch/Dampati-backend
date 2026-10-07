package org.dampati.backend.controller;

import org.dampati.backend.entity.User;
import org.dampati.backend.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.dampati.backend.security.JwtService;


import java.util.Map;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {

        User loggedInUser = userService.loginUser(
                user.getEmail(),
                user.getPassword()
        );
        if (loggedInUser == null) {
            return Map.of(
                    "message", "Invalid email or password"
            );
        }
        return Map.of(
                "token", jwtService.generateToken(loggedInUser.getEmail()),
                "user", loggedInUser
        );
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser
    )

    {
        return userService.updateUser(id, updatedUser);
    }
    @PutMapping("/reset-password/{id}")
    public String resetPassword(
        @PathVariable Long id,
        @RequestBody Map<String, String> data
    ) {
        userService.resetPassword(id, data.get("password"));
        return "Password reset successfuly";
    }
}