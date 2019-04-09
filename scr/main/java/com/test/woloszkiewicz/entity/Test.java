package com.test.woloszkiewicz.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tests")
public class Test {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idtest;
	
	//dostępny do włączenia niedostępny.
	@Column(name="available")
	private Boolean available;
	//ocena
	@Column(name = "result")
	private Double result;
	
	//iloś punktów
	@Column(name = "score")
	private Double score;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_start")
	private Date date_of_start;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_of_end")
	private Date date_of_end;
	
	@NotNull // po pierwszym włączeniu przez użytkownika 
	private Boolean first_init;
	
	@NotNull // czytest został oceniony 
	private Boolean rating;
	  
	
	/*@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "tests")
	private Set<User> users = new HashSet<>();
	*/
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	private User user;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	private Testsetting test_setting;
	
	/*@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "questions_has_tests", joinColumns = {
			@JoinColumn(name = "tests_idtest") }, inverseJoinColumns = { @JoinColumn(name = "questions_idquestion") })
	private Set<Question> questions = new HashSet<>();*/
	
	@OneToMany(mappedBy = "question")
    private Set<TestQuestionDeliveryAgent> question;
	
	
	public Test(Boolean available, User user) {
		super();
		this.available = available;
		this.user=user;
	}

	public Test() {
		
	} 
	
	public Test(Boolean available, Double result, Double score, Date date_of_start, Date date_of_end,
			Boolean first_init) {
		super();
		this.available = available;
		this.result = result;
		this.score = score;
		this.date_of_start = date_of_start;
		this.date_of_end = date_of_end;
		this.first_init = first_init;
	}	
	
	public Boolean getTestEvenStart() {
		return getTestsetting().getEverRun();
	}

	public Set<TestQuestionDeliveryAgent> getQuestion() {
		return question;
	}

	public void setQuestion(Set<TestQuestionDeliveryAgent> question) {
		this.question = question;
	}

	public Integer getUserTestId() {
		Integer id=this.user.getIdusername();
		return id;
	}
	
	public String getNameAndSurnameUsersTest() {
		String userNameAndSurname = this.user.getName()+" "+this.user.getSurname();
		return userNameAndSurname;
	}
	
	public Date dateOfTest() {
		Date date=this.test_setting.getDate_of_test();
		return date;
	}
	
	public Double posibleMaxScore() {
		Double maxscore=this.test_setting.getPossible_max_score();
		
		return maxscore;
	}
	
	public String getNameofCurseTest() {
		String nameofcursetest=this.test_setting.getCourse().getCoursename();
		return nameofcursetest;
	}

	public Date getDate_of_start() {
		return date_of_start;
	}

	public void setDate_of_start(Date date_of_start) {
		this.date_of_start = date_of_start;
	}

	public Date getDate_of_end() {
		return date_of_end;
	}

	public void setDate_of_end(Date date_of_end) {
		this.date_of_end = date_of_end;
	}

	public Boolean getFirst_init() {
		return first_init;
	}

	public void setFirst_init(Boolean first_init) {
		this.first_init = first_init;
	}

	public Integer getIdtest() {
		return idtest;
	}

	public void setIdtest(Integer idtest) {
		this.idtest = idtest;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Double getResult() {
		return result;
	}

	public void setResult(Double result) {
		this.result = result;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Testsetting getTestsetting() {
		return test_setting;
	}

	public void setTestsetting(Testsetting testsetting) {
		this.test_setting = testsetting;
	}

	public Integer getTestsettingId() {
		return test_setting.getIdsettings();
	}

	public Boolean getRating() {
		return rating;
	}

	public void setRating(Boolean rating) {
		this.rating = rating;
	}
	
	public Double getPosibleMaxScore() {
		return this.getTestsetting().getPossible_max_score();
	}

	public Boolean getSingleStatus() {
		return this.getTestsetting().getSingle();
	}
	/*public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}*/
	
	public void ratingTest() {
		Double possible_max_score = getTestsetting().getPossible_max_score();
		Double point=0.0;
		
		Set<TestQuestionDeliveryAgent> questionInTest = getQuestion();
		
		for (TestQuestionDeliveryAgent testQuestionDeliveryAgent : questionInTest) {
			List<Integer> idCorectAnswere = testQuestionDeliveryAgent.getQuestion().getIdCorectAnswere();
			List<Integer> idansweres = testQuestionDeliveryAgent.getAnswere();
			Integer numberofcorect=idCorectAnswere.size();
			Integer numberofanswere=idansweres.size();
			
			if(getSingleStatus()) {
				Double point_for_corect = getTestsetting().getSystem_score().getPoint_for_corect();
				if(numberofcorect == numberofanswere && idansweres.containsAll(idCorectAnswere)) {
					point=point+point_for_corect;
				}
				
			}else if(getTestsetting().getSystem_score().getOnly_corect()) {
				Double point_for_corect = getTestsetting().getSystem_score().getPoint_for_corect();
				if(numberofcorect == numberofanswere && idansweres.containsAll(idCorectAnswere)) {
					point=point+point_for_corect;
				}
				
			}else {
				Double point_for_corect = getTestsetting().getSystem_score().getPoint_for_corect();
				Double point_for_incorect = getTestsetting().getSystem_score().getPoint_for_incorect();
				if(numberofcorect == numberofanswere && idansweres.containsAll(idCorectAnswere)) {
					point=point+point_for_corect;
				}else if(numberofcorect != numberofanswere && idansweres.containsAll(idCorectAnswere)) {
					Double pointforquestion=point_for_corect;
					pointforquestion=pointforquestion-((numberofanswere-numberofcorect)*point_for_incorect);
					if(pointforquestion>0) {
						point=point+pointforquestion;
					}
					
				}
				
			}
		}
		setScore(point);
		setResult(getTestsetting().getSystem_result().getresult(possible_max_score, point));	
	}
	
	
	
	
	

}
