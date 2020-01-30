package com.a2.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="appuser_role")
public class AppUserRole {
	
	@Id
	private int id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private AppUser appUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Role role;
	
	public AppUserRole() {}

	public AppUserRole(AppUser appUser, Role role) {
		super();
		this.appUser = appUser;
		this.role = role;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
