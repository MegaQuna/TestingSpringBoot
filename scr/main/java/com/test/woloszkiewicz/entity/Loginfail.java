package com.test.woloszkiewicz.entity;


import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="loginfail")
public class Loginfail {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idlogfail;
		
	@NotNull
	@Size(max=50)
	private String username;
	
	private Integer numberloginfail;
	
	@NotNull
	private Boolean lockfail;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_lock_out;
	
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
    private User user;

	public Loginfail() {
		super();
	}

	public Loginfail(@NotNull @Size(max = 50) String username, Integer numberloginfail, @NotNull Boolean lockfail,
			@NotNull User user) {
		super();
		this.username = username;
		this.numberloginfail = numberloginfail;
		this.lockfail = lockfail;
		this.user = user;
	}
	
	public void addFailLogin() {
		setNumberloginfail(numberloginfail+1);
		if(getNumberloginfail()>3) {
			setLockfail(true);
			
			final long ONE_MINUTE_IN_MILLIS=60000;

			Calendar date = Calendar.getInstance();
			long t= date.getTimeInMillis();
			Date afterAddingTenMins=new Date(t + (10 * ONE_MINUTE_IN_MILLIS));
			
			setDate_of_lock_out(afterAddingTenMins);
		}
		
	}

	public Integer getIdlogfail() {
		return idlogfail;
	}

	public void setIdlogfail(Integer idlogfail) {
		this.idlogfail = idlogfail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getNumberloginfail() {
		return numberloginfail;
	}

	public void setNumberloginfail(Integer numberloginfail) {
		this.numberloginfail = numberloginfail;
	}

	public Boolean getLockfail() {
		return lockfail;
	}

	public void setLockfail(Boolean lockfail) {
		this.lockfail = lockfail;
	}

	public Date getDate_of_lock_out() {
		return date_of_lock_out;
	}

	public void setDate_of_lock_out(Date date_of_lock_out) {
		this.date_of_lock_out = date_of_lock_out;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
