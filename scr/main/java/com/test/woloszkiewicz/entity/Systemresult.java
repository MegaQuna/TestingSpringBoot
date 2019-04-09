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
    @NamedQuery(name="Systemresult.findOneById",
                query="select u from Systemresult u where u.idsystemResult = ?1"),
})
@Table(name="system_result")
public class Systemresult {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idsystemResult;
	
	@Size(max=100)
	private String name;
	
	@NotNull
	private Double three;
	
	@NotNull
	private Double threeAndHalf;
	
	@NotNull
	private Double four;
	
	@NotNull
	private Double fourAndHalf;
	
	@NotNull
	private Double five;
	
	private Boolean archive;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "system_result")
	private Set<Testsetting> test_setting;

	public Systemresult() {
		super();
	}

	public Systemresult(@NotNull @Size(max = 1) Double three, @NotNull @Size(max = 1) Double threeAndHalf,
			@NotNull @Size(max = 1) Double four, @NotNull @Size(max = 1) Double fourAndHalf,
			@NotNull @Size(max = 1) Double five) {
		super();
		this.three = three;
		this.threeAndHalf = threeAndHalf;
		this.four = four;
		this.fourAndHalf = fourAndHalf;
		this.five = five;
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

	public Integer getIdsystemResult() {
		return idsystemResult;
	}

	public void setIdsystemResult(Integer idsystemResult) {
		this.idsystemResult = idsystemResult;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getThreeAndHalf() {
		return threeAndHalf;
	}

	public void setThreeAndHalf(Double threeAndHalf) {
		this.threeAndHalf = threeAndHalf;
	}

	public Double getFourAndHalf() {
		return fourAndHalf;
	}

	public void setFourAndHalf(Double fourAndHalf) {
		this.fourAndHalf = fourAndHalf;
	}

	public Integer getIdsystem_result() {
		return idsystemResult;
	}

	public void setIdsystem_result(Integer idsystem_result) {
		this.idsystemResult = idsystem_result;
	}

	public Double getThree() {
		return three;
	}

	public void setThree(Double three) {
		this.three = three;
	}

	public Double getFour() {
		return four;
	}

	public void setFour(Double four) {
		this.four = four;
	}

	public Double getFive() {
		return five;
	}

	public void setFive(Double five) {
		this.five = five;
	}

	public Set<Testsetting> getTest_setting() {
		return test_setting;
	}

	public void setTest_setting(Set<Testsetting> test_setting) {
		this.test_setting = test_setting;
	}
	
	public String getToString() {
		int int3=(int)(this.getThree()*100);
		int int34=(int)(this.getThreeAndHalf()*100);
		int int4=(int)(this.getFour()*100);
		int int45=(int)(this.getFourAndHalf()*100);
		int int5=(int)(this.getFive()*100);
		String toReturn = "3-"+String.valueOf(int3)+"% 3,5-"+String.valueOf(int34)+"% 4-"+String.valueOf(int4)+"% 4,5-"+String.valueOf(int45)+"% 5-"+String.valueOf(int5)+"%";		
		return toReturn;
	}
	
	public Double getresult(Double maxScore, Double score) {
		Double percent=score/maxScore;
		if(percent < this.getThree()) {
			return 2.0;
		}else if(percent >= this.getThree() && percent < this.getThreeAndHalf()){
			return 3.0;
		}else if(percent >= this.getThreeAndHalf() && percent < this.getFour()) {
			return 3.5;
		}else if(percent >= this.getFour() && percent < this.getFourAndHalf()) {
			return 4.0;
		}else if(percent >= this.getFourAndHalf() && percent < this.getFive()) {
			return 4.5;
		}else {
			return 5.0;
		}
	}

}
