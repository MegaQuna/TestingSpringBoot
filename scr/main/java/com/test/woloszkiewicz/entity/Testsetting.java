package com.test.woloszkiewicz.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
    @NamedQuery(name="Testsetting.findAllOrderByDate_of_test",
                query="select u from Testsetting u order by (CASE WHEN u.date_of_test IS NULL THEN 1 ELSE 0 END) DESC , u.date_of_test DESC"),
    @NamedQuery(name="Testsetting.findOneById",
    			query="select u from Testsetting u where u.idsettings = ?1")
})
@Table(name="tests_settings")
public class Testsetting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idsettings;
	
	@NotNull
	@Size(max=40)
	@Column(name = "name_of_test")
	private String name_of_test;
	
	@NotNull
	@Column(name = "number_of_questions")
	private Integer number_of_questions;
	
	@NotNull
	@Column(name = "single")
	private Boolean single;
	
	@NotNull
    @Column(name = "time_for_test")
	private Integer time_for_test;
	
	// data kiedy egzamin został pierwszy raz uruchomiony
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_test")
	private Date date_of_test;
	
	private Double possible_max_score;
	
	private Integer dificulty;
	
	//mówi czy test został zakończony do skasowania
	@NotNull 
	private Boolean test_end;
	
	//mówi czy kiedykolwiek został uruchomiony dla kogokolwiek
	@NotNull 
	private Boolean everRun;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "test_setting")
	private Set<Test> tests;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Systemscore system_score;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Systemresult system_result;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	private Course course;
	
	public Testsetting() {
		super();
	}

	public Testsetting(@NotNull @Size(max = 100) String name_of_test, @NotNull Integer number_of_questions,
			@NotNull Boolean single, @NotNull @Size(max = 60) String system_score, Date date_of_start, Date date_of_end,
			@NotNull Integer time_for_test, @NotNull Date date_of_test) {
		super();
		this.name_of_test = name_of_test;
		this.number_of_questions = number_of_questions;
		this.single = single;
		this.time_for_test = time_for_test;
		this.date_of_test = date_of_test;
	}
	
	public Boolean getUserInitTest(Integer iduser) {
		for (Test test : getTests()) {
			if(iduser == test.getUser().getIdusername()) {
				return test.getFirst_init();
			}
			
		}
		return false;
	}
	
	public Boolean getEverRun() {
		return everRun;
	}

	public void setEverRun(Boolean everRun) {
		this.everRun = everRun;
	}

	public Integer getDificulty() {
		return dificulty;
	}

	public void setDificulty(Integer dificulty) {
		if(dificulty > 6) {
			this.dificulty = 6;	
		}else if(dificulty < 1) {
			this.dificulty = 1;
		}else {
			this.dificulty = dificulty;
		}
	}

	public Double getsystemScoreCorectPoint() {
		Double scoreCorectPoint=this.system_score.getPoint_for_corect();
		return scoreCorectPoint;
	}
	
	public Double getsystemScoreIncorectPoint() {
		Double scoreIncorectPoint=this.system_score.getPoint_for_incorect();
		return scoreIncorectPoint;
	}
	
	public Boolean getsystemScoreOnlyCorect() {
		Boolean OnlyCorect=this.system_score.getOnly_corect();
		return OnlyCorect;
	}
	
	public String getCourseName() {
		String name=this.course.getCoursename();
		return name;
	}
	
	
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Systemscore getSystem_score() {
		return system_score;
	}

	public void setSystem_score(Systemscore system_score) {
		this.system_score = system_score;
	}

	public Systemresult getSystem_result() {
		return system_result;
	}

	public void setSystem_result(Systemresult system_result) {
		this.system_result = system_result;
	}

	public Double getPossible_max_score() {
		return possible_max_score;
	}

	public void setPossible_max_score(Double possible_max_score) {
		this.possible_max_score = possible_max_score;
	}

	public Integer getIdsettings() {
		return idsettings;
	}

	public void setIdsettings(Integer idsettings) {
		this.idsettings = idsettings;
	}

	public String getName_of_test() {
		return name_of_test;
	}

	public void setName_of_test(String name_of_test) {
		this.name_of_test = name_of_test;
	}

	public Integer getNumber_of_questions() {
		return number_of_questions;
	}

	public void setNumber_of_questions(Integer number_of_questions) {
		this.number_of_questions = number_of_questions;
	}

	public Boolean getSingle() {
		return single;
	}

	public void setSingle(Boolean single) {
		this.single = single;
	}

	public Integer getTime_for_test() {
		return time_for_test;
	}

	public void setTime_for_test(Integer time_for_test) {
		if(time_for_test >90) {
			this.time_for_test = 90;
		}else if(time_for_test < 1) {
			this.time_for_test = 0;
		}else {
			this.time_for_test = time_for_test;
		}
		
	}

	public Date getDate_of_test() {
		return date_of_test;
	}

	public void setDate_of_test(Date date_of_test) {
		this.date_of_test = date_of_test;
	}

	public Set<Test> getTests() {
		return tests;
	}
	
	public List<Test> getTestsList() {
		List<Test>tests = new ArrayList<Test>(this.tests);
		Collections.sort(tests, new Comparator<Object>() {
            @Override
            public int compare(Object softDrinkOne, Object softDrinkTwo) {
                return ((Test)softDrinkOne).getUser().getSurname()
                        .compareTo(((Test)softDrinkTwo).getUser().getSurname());
            }
        }); 
		return  tests;
	}
	
	public void setTests(Set<Test> tests) {
		this.tests = tests;
	}

	public Boolean getTest_end() {
		return test_end;
	}

	public void setTest_end(Boolean test_end) {
		this.test_end = test_end;
	}
	
	public String getSystemResultToString() {
		return this.getSystem_result().getToString();
	}
	
	public Boolean getWasRating() {
		for (Test test : this.getTests()) {
			if(test.getRating()) {
				return true;
			}
		}
		return false;
	}
	
	public Boolean getCorrect() {
		if(getTime_for_test() >0 && getNumber_of_questions()>0 && getSystem_result() != null && getSystem_result() != null) {
			return true;
		}else {
			return false;
		}
		
		
	}
	
	public Boolean getAllTestEnd() {
		for (Test test : getTests()) {
			if(test.getAvailable()) {
				return false;
			}
		}
		return true;
	}
	
}
