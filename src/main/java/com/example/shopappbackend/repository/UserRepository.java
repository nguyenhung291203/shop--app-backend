package com.example.shopappbackend.repository;

import com.example.shopappbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumberAndPassword(String phoneNumber, String password);

    User findUserByPhoneNumber(String phoneNumber);
}
