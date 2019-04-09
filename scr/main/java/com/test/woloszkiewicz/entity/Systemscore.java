package com.test.woloszkiewicz.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
    @NamedQuery(name="Systemscore.findOneById",
                query="select u from Systemscore u where u.idsystemscore = ?1"),
})
@Table(name="system_score")
public class Systemscore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idsystemscore;
	
	@NotNull
	private Boolean single;
	
	@Size(max=200)
	private String description;
	
	@NotNull
	private Boolean only_corect;
	
	private Double point_for_corect;
	
	private Double point_for_incorect;
	
	private Boolean archive;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "system_score")
	private Set<Testsetting> test_setting;

	public Systemscore() {
		super();
	}

	public Systemscore(@NotNull Boolean single, @Size(max = 200) String description, @NotNull Boolean only_corect,
			Double point_for_corect, Double point_for_incorect) {
		super();
		this.single = single;
		this.description = description;
		this.only_corect = only_corect;
		this.point_for_corect = point_for_corect;
		this.point_for_incorect = point_for_incorect;
	}
	
	public Boolean canBeChange() {
		if(this.getTest_setting().isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean getArchive() {
		return archive;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	public Integer getIdsystemscore() {
		return idsystemscore;
	}

	public void setIdsystemscore(Integer idsystemscore) {
		this.idsystemscore = idsystemscore;
	}

	public Boolean getSingle() {
		return single;
	}

	public void setSingle(Boolean single) {
		this.single = single;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getOnly_corect() {
		return only_corect;
	}

	public void setOnly_corect(Boolean only_corect) {
		this.only_corect = only_corect;
	}

	public Double getPoint_for_corect() {
		return point_for_corect;
	}

	public void setPoint_for_corect(Double point_for_corect) {
		this.point_for_corect = point_for_corect;
	}

	public Double getPoint_for_incorect() {
		return point_for_incorect;
	}

	public void setPoint_for_incorect(Double point_for_incorect) {
		this.point_for_incorect = point_for_incorect;
	}

	public Set<Testsetting> getTest_setting() {
		return test_setting;
	}

	public void setTest_setting(Set<Testsetting> test_setting) {
		this.test_setting = test_setting;
	}
	
	
	
	
	
	
}
