package org.dampati.backend.service;

import org.dampati.backend.entity.User;

import org.dampati.backend.repository.UserRepository;
import org.dampati.backend.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
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
    public void resetPassword(Long id, String newPassword) {
        User user = userRepository.findById(id).orElse(null);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}