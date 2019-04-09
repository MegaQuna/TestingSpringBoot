package com.test.woloszkiewicz.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="newUser")
public class NewUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idnewUser;
	
	@NotNull
	@Size(max=25)
	@Column(name="name")
	private String name;
	
	@NotNull
	@Size(max=30)
	@Column(name="surname")
	private String surname;
	
	@NotNull
	@Size(max=30)
	@Column(name="username")
	private String username;
	
	@NotNull
	@Size(max=35)
	@Column(name="email")
	private String email;
	
	@NotNull
	@Size(max = 80)
	private String password;
	
	@Size(max=200)
	@Column(name="description")
	private String description;

	public NewUser() {
		super();
	}

	public NewUser(@NotNull @Size(max = 25) String name, @NotNull @Size(max = 30) String surname,
			@NotNull @Size(max = 30) String username, @NotNull @Size(max = 35) String email,
			@NotNull @Size(max = 80) String password, @Size(max = 200) String description) {
		super();
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.description = description;
	}

	public Integer getIdnewUser() {
		return idnewUser;
	}

	public void setIdnewUser(Integer idnewUser) {
		this.idnewUser = idnewUser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
 
}
