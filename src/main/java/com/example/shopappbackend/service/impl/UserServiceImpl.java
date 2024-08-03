package com.example.shopappbackend.service.impl;

import com.example.shopappbackend.dto.UserLoginDTO;
import com.example.shopappbackend.dto.UserRegisterDTO;
import com.example.shopappbackend.exception.BadRequestException;
import com.example.shopappbackend.exception.DataIntegrityViolationException;
import com.example.shopappbackend.exception.NotFoundException;
import com.example.shopappbackend.jwt.JwtTokenProvider;
import com.example.shopappbackend.mapper.UserMapping;
import com.example.shopappbackend.model.Role;
import com.example.shopappbackend.model.User;
import com.example.shopappbackend.repository.RoleRepository;
import com.example.shopappbackend.repository.UserRepository;
import com.example.shopappbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Value("${app-jwt-expiration-milliseconds}")
    private Long expirationDate;

    @Override
    public User register(UserRegisterDTO userRegisterDTO) {
        if (this.userRepository.existsUserByPhoneNumber(userRegisterDTO.getPhoneNumber()))
            throw new DataIntegrityViolationException("Số điện thoại đã tồn tại");
        if (!userRegisterDTO.getPassword().equals(userRegisterDTO.getRetypePassword()))
            throw new BadRequestException("Mật khẩu và mật khẩu nhập lại không giống nhau");
        User user = UserMapping.mapUserRegisterDTOtoUser(userRegisterDTO);
        Role role = roleRepository.findById(userRegisterDTO.getRoleId()).orElseThrow(() -> new NotFoundException("Không tìm thấy role có id: " + userRegisterDTO.getRoleId()));
        user.setRole(role);
        if (userRegisterDTO.getFacebookAccountId() == null && userRegisterDTO.getGoogleAccountId() == null) {
            user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        User user = this.userRepository.findUserByPhoneNumber(userLoginDTO.getPhoneNumber());

        if (user == null)
            throw new BadRequestException("Tài khoản hoặc mật khẩu không chính xác");
        if (user.getFacebookAccountId() == null
                && user.getGoogleAccountId() == null) {
            if (!passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword()))
                throw new BadRequestException("Tài khoản hoặc mật khẩu không chính xác");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
