package com.test.woloszkiewicz.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
    @NamedQuery(name="Group.findOneByid",
                query="select u from Group u where u.idgroups = ?1"),
})
@Table(name="groups")
public class Group {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idgroups;
	
	@NotNull
	@Size(max=60)
	@Column(name="groups_name", unique = true)
	private String groupname;
	
	@Size(max=200)
	private String description;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "create_date")
	private Date createdate;
	
	@OrderBy("surname ASC")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "groups")
	private Set<User> users = new HashSet<>();
	
	@OrderBy("coursename ASC")
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "groups_has_courses", joinColumns = {
			@JoinColumn(name = "groups_idgroups") }, inverseJoinColumns = { @JoinColumn(name = "courses_idcourse") })
	private Set<Course> courses = new HashSet<>();
	
	
	public Group() {
		super();
	}

	public Group(@NotNull @Size(max = 60) String groupname, @Size(max = 200) String description) {
		super();
		this.groupname = groupname;
		this.description = description;
	}
	
	public Date getCreate_date() {
		return createdate;
	}

	public void setCreate_date(Date create_date) {
		this.createdate = create_date;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public Integer getIdgroups() {
		return idgroups;
	}

	public void setIdgroups(Integer idgroups) {
		this.idgroups = idgroups;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Set<User> getUsersNotInGroup(List<User> users) {
		Set<User> toSend=this.getUsers();
		for (User user : users) {
			toSend.remove(user);
		}
		return toSend;
	}
	

}
