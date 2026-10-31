package org.dampati.backend.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table (name = "password_reset_otps")
public class PasswordResetOtp {
    public LocalDateTime getVerifiedExpiryTime() {
        return verifiedExpiryTime;
    }

    public void setVerifiedExpiryTime(LocalDateTime verifiedExpiryTime) {
        this.verifiedExpiryTime = verifiedExpiryTime;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getOtpHash() {
        return otpHash;
    }

    public void setOtpHash(String otpHash) {
        this.otpHash = otpHash;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public void setAttemptCount(int attemptCount) {
        this.attemptCount = attemptCount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "otp_hash", nullable = false)
    private String otpHash;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    @Column(nullable = false)
    private boolean verified = false;

    @Column(name = "verified_expiry_time")
    private LocalDateTime verifiedExpiryTime;

    @Column(name = "attempt_count", nullable = false)
    private int attemptCount = 0;
}
