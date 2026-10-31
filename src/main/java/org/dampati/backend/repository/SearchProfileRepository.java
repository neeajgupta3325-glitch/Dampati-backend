package org.dampati.backend.repository;

import org.dampati.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchProfileRepository extends JpaRepository<User, Long> {
}
