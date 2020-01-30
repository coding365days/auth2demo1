package com.a2.controllers;

import static com.a2.config.SecurityConstants.TOKEN_PREFIX;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a2.config.JwtTokenProvider;
import com.a2.config.SecurityUtility;
import com.a2.models.AppUser;
import com.a2.models.AppUserRole;
import com.a2.models.JWTLoginSuccessResponse;
import com.a2.models.LoginCredentials;
import com.a2.models.Role;
import com.a2.services.impl.AppUserServiceImpl;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AppUserServiceImpl appUserService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<AppUser> register() {
		AppUser appUser = new AppUser();
//		appUser.setUsername(aUser.getUsername());
//		appUser.setPassword(aUser.getPassword());
//		appUser.setFullname(aUser.getFullname());
		
		appUser.setUsername("kiran@email.com");
		appUser.setPassword(SecurityUtility.passwordEncoder().encode("pass1"));
		appUser.setFullname("kiran kumar");
		
		Set<AppUserRole> appUserRoleSet = new HashSet<>();
		Role role = new Role();
		role.setId("U");
		role.setType("ROLE_USER");
		appUserRoleSet.add(new AppUserRole(appUser, role));
		appUserService.save(appUser, appUserRoleSet);
		
		return new ResponseEntity<AppUser>(appUser, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginCredentials loginCredentials) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
				new UsernamePasswordAuthenticationToken(loginCredentials.getUsername(), loginCredentials.getPassword());
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtToken = TOKEN_PREFIX + jwtTokenProvider.tokenGeneration(authentication);
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwtToken));
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping("/delete")
	public String delete() {
		return "delete";
	}
}
