package ru.example.springsecurity.service;

import ru.example.springsecurity.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
}
