package com.api.zoo.enums;

public enum RoleEnum {
    ADMIN("ADMIN"),
    EMPLEADO("EMPLEADO");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}