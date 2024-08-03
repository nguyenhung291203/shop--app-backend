package com.example.shopappbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fullname", length = 100)
    private String fullName;
    @Column(name = "phone_number", length = 10, nullable = false, unique = true)
    private String phoneNumber;
    @Column(length = 100)
    @JsonIgnore
    private String password;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "facebook_account_id")
    private Long facebookAccountId;
    @Column(name = "google_account_id")
    private Long googleAccountId;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
