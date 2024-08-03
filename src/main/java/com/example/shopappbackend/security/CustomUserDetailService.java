package com.example.shopappbackend.security;

import com.example.shopappbackend.exception.NotFoundException;
import com.example.shopappbackend.model.User;
import com.example.shopappbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findUserByPhoneNumber(phoneNumber);
        if (user == null)
            throw new NotFoundException("Tài khoản không tồn tại");
        return CustomUserDetails.mapUserToUserCustomDetails(user);
    }
}
