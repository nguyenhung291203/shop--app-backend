package com.example.shopappbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "social_accounts")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20,nullable = false)
    private String provider;
    @Column(name = "provider_id",nullable = false,length = 50)
    private String providerId;
    @Column(length = 150,nullable = false)
    private String email;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
