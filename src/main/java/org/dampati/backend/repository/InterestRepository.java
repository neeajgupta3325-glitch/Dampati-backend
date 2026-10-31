package org.dampati.backend.repository;


import org.dampati.backend.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long> {

    List<Interest> findBySender_Id(Long senderId);
    List<Interest> findByReceiver_Id(Long receiverId);
    Optional<Interest> findBySender_IdAndReceiver_Id(
            Long senderid,
            Long receiverid
    );
}
