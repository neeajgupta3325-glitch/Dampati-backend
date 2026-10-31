package org.dampati.backend.entity;

import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class PartnerPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String preferredGender;
    private String preferredReligion;
    private String preferredMotherTongue;

    public String getPreferredReligion() {
        return preferredReligion;
    }

    public void setPreferredReligion(String preferredReligion) {
        this.preferredReligion = preferredReligion;
    }

    private String preferredLocation;
    private String preferredEducation;
    private String preferredOccupation;

    private Integer minAge;
    private Integer maxAge;

    public String getPreferredGender() {
        return preferredGender;
    }

    public void setPreferredGender(String preferredGender) {
        this.preferredGender = preferredGender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public String getPreferredEducation() {
        return preferredEducation;
    }

    public void setPreferredEducation(String preferredEducation) {
        this.preferredEducation = preferredEducation;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }

    public String getPreferredMotherTongue() {
        return preferredMotherTongue;
    }

    public void setPreferredMotherTongue(String preferredMotherTongue) {
        this.preferredMotherTongue = preferredMotherTongue;
    }

    public String getPreferredOccupation() {
        return preferredOccupation;
    }

    public void setPreferredOccupation(String preferredOccupation) {
        this.preferredOccupation = preferredOccupation;
    }
}
