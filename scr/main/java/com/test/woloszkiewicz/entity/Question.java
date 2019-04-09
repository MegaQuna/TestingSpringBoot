package com.test.woloszkiewicz.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.tomcat.util.codec.binary.Base64;

@Entity
@NamedQueries({
    @NamedQuery(name="Question.findBySingleAndDificulty",
                query="select u from Question u where u.single = ?1 and u.dificulty = ?2"),
    @NamedQuery(name="Question.findOneById",
    query="select u from Question u where u.idquestion = ?1")
})
@Table(name="questions")
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idquestion;
	
	@NotNull
	@Size(max=200)
	private String content_question;
	
	@NotNull
	private Boolean single;
	
	@NotNull
	private Integer dificulty;
	
	@NotNull
	private Boolean archive;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	private Course course;
	
	@OrderBy("idanswere ASC")
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question")
	private Set<Answere> answeres;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "question")
	private ImgFileQuestion imgFile;
	
	@OneToMany(mappedBy = "test")
    private Set<TestQuestionDeliveryAgent> test;

	public String generateBase64Image()
	{
	    return Base64.encodeBase64String(this.imgFile.getData());
	}

	public ImgFileQuestion getImgFile() {
		if(this.imgFile != null) {
			return imgFile;
		}else {
			return null;
		}
		
	}
	
	public boolean replace(Question question) {
		if (this.getIdquestion() == question.getIdquestion()) {
			this.setAnsweres(question.getAnsweres());
			this.content_question = question.getContent_question();
			this.course=question.getCourse();
			this.dificulty=question.getDificulty();
			this.imgFile=question.getImgFile();
			this.single=question.getSingle();
			this.test=question.getTest();
			
			return true;

		} else {
			return false;
		}
	}
	
	public String getCourseName() {
		if(this.getCourse()!=null ) {
			return this.getCourse().getCoursename();
		}else {
			return "Brak";
		}
		
	}
	

	public Boolean getArchive() {
		return archive;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	public void setImgFile(ImgFileQuestion imgFile) {
		this.imgFile = imgFile;
	}

	public Integer getIdquestion() {
		return idquestion;
	}

	public void setIdquestion(Integer idquestion) {
		this.idquestion = idquestion;
	}

	public String getContent_question() {
		return content_question;
	}

	public void setContent_question(String content_question) {
		this.content_question = content_question;
	}

	public Boolean getSingle() {
		return single;
	}

	public void setSingle(Boolean single) {
		this.single = single;
	}

	public Integer getDificulty() {
		return dificulty;
	}

	public void setDificulty(Integer dificulty) {
		this.dificulty = dificulty;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Set<Answere> getAnsweres() {
		return answeres;
	}

	public void setAnsweres(Set<Answere> answeres) {
		this.answeres = answeres;
	}

	public Set<TestQuestionDeliveryAgent> getTest() {
		return test;
	}

	public void setTest(Set<TestQuestionDeliveryAgent> test) {
		this.test = test;
	}
	
	public List<Integer> getIdCorectAnswere(){
		List<Integer> idansweres= new ArrayList<Integer>();
		for (Answere answere : getAnsweres()) {
			if(answere.getCorrect()) {
				idansweres.add(answere.getIdanswere());
			}
		}
		return idansweres;
	}
	
}
