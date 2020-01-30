package com.a2.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Role {

	@Id
	private String id;
    private String type;
    
	@OneToMany(mappedBy="role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<AppUserRole> userRole = new HashSet<>();
	
	public Role() {}

	public Role(String id, String type, Set<AppUserRole> userRole) {
		super();
		this.id = id;
		this.type = type;
		this.userRole = userRole;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<AppUserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<AppUserRole> userRole) {
		this.userRole = userRole;
	}
	
}
