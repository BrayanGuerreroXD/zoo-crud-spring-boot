package com.api.zoo.service;

import com.api.zoo.entity.Role;

public interface RoleService {
    Role findByName(String name);
}
