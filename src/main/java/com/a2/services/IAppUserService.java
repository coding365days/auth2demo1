package com.a2.services;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.a2.models.AppUser;
import com.a2.models.AppUserRole;

@Service
public interface IAppUserService extends UserDetailsService{

	public void save(AppUser appUser, Set<AppUserRole> appUserRoleSet);
}
