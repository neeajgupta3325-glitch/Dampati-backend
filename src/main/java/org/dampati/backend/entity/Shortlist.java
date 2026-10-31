package org.dampati.backend.entity;


import jakarta.persistence.*;

@Entity
public class Shortlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "shortlisted_user_id", nullable = false)
    private User shortlistedUser;

    public Shortlist() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getShortlistedUser() {
        return shortlistedUser;
    }

    public void setShortlistedUser(User shortlistedUser) {
        this.shortlistedUser = shortlistedUser;
    }
}
