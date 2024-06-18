package com.api.zoo.service.impl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.api.zoo.entity.Role;
import com.api.zoo.repository.RoleRepository;
import com.api.zoo.service.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        Role role = roleRepository.findByName(name);
        if (Objects.isNull(role))
            throw new RuntimeException("Role not found");
        return role;
    }
    
}
