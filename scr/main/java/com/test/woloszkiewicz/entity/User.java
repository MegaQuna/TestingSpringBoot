package com.test.woloszkiewicz.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
    @NamedQuery(name="User.findOneByid", query="select u from User u where u.idusername = ?1"),
    @NamedQuery(name = "User.findOneByUsername", query = "select u from User u where u.username = ?1"),
    @NamedQuery(name = "User.existUsername", query = "select COUNT(u) from User u where u.username = ?1"),
    @NamedQuery(name = "User.existEmail", query = "select COUNT(u) from User u where u.email = ?1"),
})
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idusername;
	
	@NotNull
	@Size(max=60)
	private String name;
	
	@NotNull
	@Size(max=80)
	private String surname;

	@NotNull
	@Size(max = 50)
	@Column(unique = true)
	private String username;

	@NotNull
	@Size(max = 80)
	private String password;

	@NotNull
	private Boolean enabled;

	@NotNull
	@Size(max = 80)
	private String email;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Test> tests;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "users_has_groups", joinColumns = {
			@JoinColumn(name = "users_idusername") }, inverseJoinColumns = { @JoinColumn(name = "groups_idgroups") })
	private Set<Group> groups = new HashSet<>();

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
	private Authority authority;
	
	@OneToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL, mappedBy = "user")
	private Loginfail loginfail;
	
	
	public User() {

	}

	public User(@Size(max = 50) String username, @NotNull @Size(max = 80) String password, @NotNull Boolean enabled,
			@NotNull @Size(max = 80) String email) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.email = email;
	}
	
	public String getAuthorityName() {
		String authoritystring=this.authority.getAuthority();
		return authoritystring;
	}
	
	public Boolean badLoginStatus() {
		if(this.loginfail ==null) {
			return null;
		}
		Boolean badlogin=this.loginfail.getLockfail();
		return badlogin;
	}
	
	public Date dateEndOfBadLoginBlock () {
		Date date=this.loginfail.getDate_of_lock_out();
		return date;
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

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Integer getIdusername() {
		return idusername;
	}

	public void setIdusername(Integer idusername) {
		this.idusername = idusername;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Test> getTestsList() {
		List<Test>tests = new ArrayList<Test>(this.tests);
		Collections.sort(tests, new Comparator<Object>() {
            @Override
            public int compare(Object softDrinkOne, Object softDrinkTwo) {
                return ((Test)softDrinkOne).getIdtest()
                        .compareTo(((Test)softDrinkTwo).getIdtest());
            }
        }); 
		return  tests;
	}

	public Set<Test> getTests() {
		return this.tests;
	}

	public void setTests(Set<Test> tests) {
		this.tests = tests;
	}

	public Loginfail getLoginfail() {
		return loginfail;
	}

	public void setLoginfail(Loginfail loginfail) {
		this.loginfail = loginfail;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	
	
	
}
