package com.example.shopappbackend.service.impl;

import com.example.shopappbackend.dto.RoleDTO;
import com.example.shopappbackend.exception.DataIntegrityViolationException;
import com.example.shopappbackend.exception.NotFoundException;
import com.example.shopappbackend.model.Role;
import com.example.shopappbackend.repository.RoleRepository;
import com.example.shopappbackend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role insertRole(RoleDTO roleDTO) {
        if (this.roleRepository.existsByName(roleDTO.getName()))
            throw new DataIntegrityViolationException("Tên role đã tồn tại");
        return this.roleRepository.save(Role.builder().name(roleDTO.getName()).build());
    }

    @Override
    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }

    @Override
    public Role updateRole(Long id, RoleDTO roleDTO) {
        Role role = this.roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy role có id: " + id));
        if (this.roleRepository.existsByName(roleDTO.getName()))
            throw new DataIntegrityViolationException("Tên role đã tồn tại");
        role.setName(role.getName());
        return this.roleRepository.save(role);
    }

    @Override
    public void deleteRoleById(Long id) {
        this.roleRepository.deleteById(id);
    }
}
