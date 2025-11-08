package com.example.danceschool.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "user_cards")
public class UserCard {

    @Id
    private UUID id;

    private boolean active;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // this creates the foreign key column
    private User user;

    public UserCard() {}

}