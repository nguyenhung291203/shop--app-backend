package com.example.shopappbackend.mapper;

import com.example.shopappbackend.dto.UserRegisterDTO;
import com.example.shopappbackend.model.User;

public class UserMapping {
    public static User mapUserRegisterDTOtoUser(UserRegisterDTO userRegisterDTO) {
        return User.builder()
                .dateOfBirth(userRegisterDTO.getDateOfBirth())
                .facebookAccountId(userRegisterDTO.getFacebookAccountId())
                .fullName(userRegisterDTO.getFullName())
                .googleAccountId(userRegisterDTO.getGoogleAccountId())
                .phoneNumber(userRegisterDTO.getPhoneNumber())
                .password(userRegisterDTO.getPassword())
                .isActive(true)
                .build();
    }
}
