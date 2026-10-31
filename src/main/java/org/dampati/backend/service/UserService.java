package org.dampati.backend.service;

import org.dampati.backend.entity.User;

import org.dampati.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already exists"
            );
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if(existingUser.isPresent()) {
            User user = existingUser.get();

            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            user.setMobile(updatedUser.getMobile());
            user.setGender(updatedUser.getGender());
            user.setDateOfBirth(updatedUser.getDateOfBirth());
            user.setReligion(updatedUser.getReligion());
            user.setMotherTongue(updatedUser.getMotherTongue());
            user.setLocation(updatedUser.getLocation());
            user.setEducation(updatedUser.getEducation());
            user.setOccupation(updatedUser.getOccupation());

            return userRepository.save(user);

        }
        return null;
    }
    public User loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent() &&
            passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        }
        return null;
    }

    @Transactional
    public void resetPassword(Long id, String newPassword) {
                if (newPassword == null || newPassword.length() < 8){
                    throw new org.springframework.web.server.ResponseStatusException(
                            org.springframework.http.HttpStatus.BAD_REQUEST,
                            "Password must contain at least 8 characters"
                    );
                }
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new org.springframework.web.server.ResponseStatusException(
                                org.springframework.http.HttpStatus.NOT_FOUND,
                                "User not found"
                        )
                );
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}