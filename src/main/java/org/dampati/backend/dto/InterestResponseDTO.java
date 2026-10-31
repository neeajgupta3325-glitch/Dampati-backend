package org.dampati.backend.dto;

import java.time.LocalDateTime;

public class InterestResponseDTO {
    private Long id;
    private String senderName;
    private String receiverName;
    private String status;
    private LocalDateTime createdAt;

    public InterestResponseDTO() {

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InterestResponseDTO(
            Long id,
            String senderName,
            String receiverName,
            String status,
            LocalDateTime createdAt) {
        this.id = id;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.status = status;
        this.createdAt = createdAt;
    }
}
