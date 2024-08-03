package com.example.shopappbackend.repository;

import com.example.shopappbackend.model.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount,Long> {
}
