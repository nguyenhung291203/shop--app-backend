package com.example.shopappbackend.service;

import com.example.shopappbackend.dto.UserLoginDTO;
import com.example.shopappbackend.dto.UserRegisterDTO;
import com.example.shopappbackend.model.Token;
import com.example.shopappbackend.model.User;

import java.util.List;

public interface UserService {
    User register(UserRegisterDTO userRegisterDTO);
    String login(UserLoginDTO userLoginDTO);
    List<User> getAllUsers();
}
