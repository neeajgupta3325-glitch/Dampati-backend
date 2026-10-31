package org.dampati.backend.service;

import org.dampati.backend.entity.PasswordResetOtp;
import org.dampati.backend.repository.PasswordResetOtpRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;



@Service
public class PasswordResetService {

    private final JavaMailSender mailSender;
    private final PasswordResetOtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(
            JavaMailSender mailSender,
            PasswordResetOtpRepository otpRepository,
            PasswordEncoder passwordEncoder) {
        this.mailSender = mailSender;
        this.otpRepository = otpRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void sendOtpEmail(String email, String otp) {
        PasswordResetOtp record = otpRepository.findByEmail(email)
                .orElseGet(PasswordResetOtp::new);

        record.setEmail(email);
        record.setOtpHash(passwordEncoder.encode(otp));
        record.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        record.setVerified(false);
        record.setVerifiedExpiryTime(null);
        record.setAttemptCount(0);

        otpRepository.save(record);


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(System.getenv("MAIL_USERNAME"));
        message.setTo(email);
        message.setSubject("Dampati - Password Reset OTP");
        message.setText(
                "Your password reset OTP is: " + otp +
                        "\nThis OTP should expire after 5 minutes."
        );
        mailSender.send(message);
    }

    @Transactional
    public boolean verifyOtp(String email, String enteredOtp) {
       PasswordResetOtp record = otpRepository.findByEmail(email)
               .orElse(null);

       if (record == null
               || record.isVerified()
               || record.getExpiryTime() == null
               || !LocalDateTime.now().isBefore(record.getExpiryTime())){
           if (record != null && !record.isVerified())  {
               otpRepository.delete(record);
           }
           return false;
       }
       // Maximum 5 incorrect attempts
        if(record.getAttemptCount() >= 5) {
            otpRepository.delete(record);
            return false;
        }

       if (!passwordEncoder.matches(enteredOtp, record.getOtpHash())) {
           record.setAttemptCount(record.getAttemptCount() +1);

           if (record.getAttemptCount() >= 5) {
               otpRepository.delete(record);

           } else {
               otpRepository.save(record);
           }

           return false;
       }

       record.setVerified(true);
       record.setVerifiedExpiryTime(LocalDateTime.now().plusMinutes(5));
       record.setOtpHash("");
       record.setExpiryTime(LocalDateTime.now());
       otpRepository.save(record);

       return true;
    }

    @Transactional(readOnly = true)
    public boolean isEmailVerified(String email) {
       PasswordResetOtp record = otpRepository.findByEmail(email)
               .orElse(null);

       return record != null
                && record.isVerified()
                && record.getVerifiedExpiryTime() != null
                && LocalDateTime.now().isBefore(record.getVerifiedExpiryTime());
    }

    @Transactional
    public boolean consumeVerification(String email) {
        PasswordResetOtp  record  = otpRepository.findByEmail(email)
                .orElse(null);

        if (record == null
                || !record.isVerified()
                || record.getVerifiedExpiryTime() == null
                || !LocalDateTime.now().isBefore(record.getVerifiedExpiryTime()))  {
            return false;
        }

        otpRepository.delete(record);
        return true;
    }

    @Transactional
    public void clearVerification(String email) {
        otpRepository.deleteByEmail(email);
    }
}
