package com.test.woloszkiewicz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="note")
public class Note {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="description")
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cereate")
	private Date cereate;

	public Note(String description, Date cereate, Date modification) {
		super();
		this.description = description;
		this.cereate = cereate;
	}

	public Note() {
		super();
	}
	
	
	
	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCereate() {
		return cereate;
	}

	public void setCereate(Date cereate) {
		this.cereate = cereate;
	}

}
