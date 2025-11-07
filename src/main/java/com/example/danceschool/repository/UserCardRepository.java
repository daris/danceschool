package com.example.danceschool.repository;

import com.example.danceschool.model.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserCardRepository extends JpaRepository<UserCard, UUID> {

//    // Find user by username
//    Optional<User> findByUsername(String username);
//
//    // Find user by email
//    Optional<User> findByEmail(String email);
//
//    // Check if a user exists by email or username
//    boolean existsByEmail(String email);
//    boolean existsByUsername(String username);
}