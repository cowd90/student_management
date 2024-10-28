package com.unit.studentmgmt.service;

import com.unit.studentmgmt.entity.Role;
import com.unit.studentmgmt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    public Role findRoleById(Long roleId) {
        return roleRepository.findById(roleId)
            .orElseThrow(() -> new RuntimeException("Role not found"));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
