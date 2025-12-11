package com.example.danceschool.userpass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserPassRepository extends JpaRepository<UserPass, UUID> {
}