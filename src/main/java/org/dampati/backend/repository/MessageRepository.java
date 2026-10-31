package org.dampati.backend.repository;

import org.dampati.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender.id = :user1 AND m.receiver.id = :user2) OR " +
            "(m.sender.id = :user2 AND m.receiver.id = :user1) " +
            "ORDER BY m.sentAt ASC")
    List<Message> findConversation(
            @Param("user1") Long user1,
            @Param("user2") Long user2);

    @Query("""
            SELECT m FROM Message m
            WHERE m.sender.id = :userId
                OR m.receiver.id = :userId
            ORDER BY m.sentAt DESC
""")
    List<Message> findUserMessages(@Param("userId") Long userId);

    @Query("SELECT COUNT(m) FROM Message" +
            " m WHERE m.receiver.id = :receiverId" +
            " AND m.sender.id = :senderId" +
            " AND m.seen = false")
    long countUnread(@Param("receiverId")
                     Long receiverId, @Param("senderId")
                     Long senderId);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET " +
            "m.seen = true WHERE " +
            "m.receiver.id = :receiverId AND " +
            "m.sender.id = :senderId AND " +
            "m.seen = false")
    int markAsSeen(@Param("receiverId") Long receiverId, @Param("senderId") Long senderId);

}
