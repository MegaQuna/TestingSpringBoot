package com.test.woloszkiewicz.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@NamedQueries({
    @NamedQuery(name="Course.findOneByid",
                query="select u from Course u where u.idcourse = ?1"),
    @NamedQuery(name="Course.findAllByArchive",
    query="select u from Course u where u.archive = ?1")
})
@Table(name="courses")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idcourse;
	
	@NotNull
	@Size(max=60)
	@Column(name="course_name", unique = true)
	private String coursename;
	
	@Size(max=200)
	@Column(name="description")
	private String description;
	
	private Boolean archive; 
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "courses")
	private Set<Group> groups = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course")
	private Set<Question> questions;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "course")
	private Set<Testsetting> testsetting;

	public Course() {
		super();
	}

	public Course(@NotNull @Size(max = 60) String coursename, @Size(max = 200) String description) {
		super();
		this.coursename = coursename;
		this.description = description;
	}

	public Boolean canWeremove() {
		if(this.getTestsetting().isEmpty() || this.getTestsetting()==null) {
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

	public Set<Question> getQuestionsBySingleAndDificulty(Boolean single, Integer dificult){
			Set<Question> list = new HashSet<Question>();
			for (Question question : questions) {
				if(question.getSingle()==single && question.getDificulty()==dificult) {
					list.add(question);
				}
			}

		return list;
	}
	
	public Set<Question> getQuestionsBySingleAndDificultyRange(Boolean single, Integer dificult){
		Set<Question> list = new HashSet<Question>();
		int rangemax;
		int rangemin;
		switch (dificult) {
		case 1:{
			rangemin=1;
			rangemax=1;
			break;
		}
		case 2:{
			rangemin=1;
			rangemax=2;
			break;
		}
		case 3:{
			rangemin=1;
			rangemax=3;
			break;
		}
		case 4:{
			rangemin=2;
			rangemax=2;
			break;
		}
		case 5:{
			rangemin=2;
			rangemax=3;
			break;
		}
		case 6:{
			rangemin=3;
			rangemax=3;
			break;
		}
		default:{
			rangemin=1;
			rangemax=1;
			break;
		}
		}
		
		for (Question question : questions) {
			if(question.getSingle()==single && question.getDificulty()>=rangemin && question.getDificulty()<=rangemax) {
				list.add(question);
			}
		}

	return list;
}

	public boolean isSelected(Integer idcourse){
        if (idcourse != null) {
        	
            return idcourse.equals(this.getIdcourse());
        }
        return false;
    } 

	public Integer getIdcourse() {
		return idcourse;
	}

	public void setIdcourse(Integer idcourse) {
		this.idcourse = idcourse;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Set<Question> getQuestions() {
		return questions;
	}
	
	public Set<Question> getQuestions(Boolean Archive, Integer dificulty, Boolean single) {
		Set<Question> questionList=new HashSet<Question>();
		for (Question question : questions) {
			if(question.getArchive()==Archive && question.getDificulty()==dificulty && question.getSingle()==single) {
				questionList.add(question);
			}	
		}
		return questionList;
	}
	
	public Set<Question> getQuestions(Boolean Archive, Integer dificultyFrom, Integer dificultyTo, Boolean single) {
		Set<Question> questionList=new HashSet<Question>();
		for (Question question : questions) {
			if(question.getArchive()==Archive && question.getDificulty()>=dificultyFrom && question.getDificulty()<=dificultyTo && question.getSingle()==single) {
				questionList.add(question);
			}	
		}
		return questionList;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Set<Testsetting> getTestsetting() {
		return testsetting;
	}

	public void setTestsetting(Set<Testsetting> testsetting) {
		this.testsetting = testsetting;
	}
	
	

}
