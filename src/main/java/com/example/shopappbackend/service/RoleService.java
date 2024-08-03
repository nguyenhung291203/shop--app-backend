package com.example.shopappbackend.service;

import com.example.shopappbackend.dto.RoleDTO;
import com.example.shopappbackend.model.Role;

import java.util.List;

public interface RoleService {
    Role insertRole(RoleDTO roleDTO);
    List<Role> getAllRoles();
    Role updateRole(Long id,RoleDTO roleDTO);
    void deleteRoleById(Long id);
}