package com.a2.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.a2.models.AppUser;
import com.a2.models.AppUserRole;
import com.a2.repositories.AppUserRepository;
import com.a2.repositories.RoleRepository;
import com.a2.services.IAppUserService;

@Service
public class AppUserServiceImpl implements IAppUserService{
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AppUser appUser = appUserRepository.findByUsername(username);
		if (appUser == null)
			new UsernameNotFoundException("The Username " + username + " is Not Found.");
		
		UserDetails authenticatedUser = new User(appUser.getUsername(), appUser.getPassword(), appUser.getAuthorities());
		return authenticatedUser;
	}

	@Override
	public void save(AppUser aUser, Set<AppUserRole> appUserRoleSet) {
		
		AppUser appUser = appUserRepository.findByUsername(aUser.getUsername());
		if(appUser != null) {
			System.out.println("Username " + aUser.getUsername() + " already exists.");
		}else {
			for(AppUserRole ur: appUserRoleSet) {
				roleRepository.save(ur.getRole());
			}
			aUser.getUserRoles().addAll(appUserRoleSet);
			appUser = appUserRepository.save(aUser);
		}
	}

}
