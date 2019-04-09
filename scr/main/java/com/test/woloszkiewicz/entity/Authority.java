package com.test.woloszkiewicz.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.access.annotation.Secured;

@Entity
@Table(name="authorities")
public class Authority {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idauthority;
	
	@NotNull
	@Size(max=50)
	private String authority;
	
	@NotNull
	@Size(max=50)
	//@Column(unique = true)
	private String username;
	
	@NotNull
	@OneToOne(fetch=FetchType.LAZY)
	private User user;
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

	public Authority() {
		super();
	}

	public Authority( @NotNull @Size(max = 50) String authority,
			@NotNull @Size(max = 50) String username) {
		super();
		this.authority = authority;
		this.username = username;
	}
	

	public Authority(@NotNull @Size(max = 50) String authority, @NotNull @Size(max = 50) String username, User user) {
		super();
		this.authority = authority;
		this.username = username;
		this.user = user;
	}

	public Integer getIdauthority() {
		return idauthority;
	}

	public void setIdauthority(Integer idauthority) {
		this.idauthority = idauthority;
	}

	public String getAuthority() {
		return authority;
	}
	
	@Secured("ROLE_ADMIN")
	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	
}
