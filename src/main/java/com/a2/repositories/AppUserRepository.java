package com.a2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.a2.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, String>{

	public AppUser findByUsername(String username);
//	public AppUser getById(Long id);
}
