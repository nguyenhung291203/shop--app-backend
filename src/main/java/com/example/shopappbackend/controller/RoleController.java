package com.example.shopappbackend.controller;

import com.example.shopappbackend.dto.RoleDTO;
import com.example.shopappbackend.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/roles")
@Validated
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping()
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping()
    public ResponseEntity<?> insertRole(@Valid @RequestBody RoleDTO roleDTO) {
        return new ResponseEntity<>(roleService.insertRole(roleDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoleById(@Valid @PathVariable Long id) {
        this.roleService.deleteRoleById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoleById(@Valid @PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        return new ResponseEntity<>(roleService.updateRole(id, roleDTO), HttpStatus.OK);
    }
}
