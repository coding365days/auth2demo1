package com.a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a2.models.Role;

public interface RoleRepository extends JpaRepository<Role, String>{

}
