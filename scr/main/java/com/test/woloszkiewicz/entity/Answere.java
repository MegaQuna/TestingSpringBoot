package com.test.woloszkiewicz.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.security.access.annotation.Secured;


@Entity
@NamedQueries({
    @NamedQuery(name="Answere.findOneById",
    query="select u from Answere u where u.idanswere = ?1")
})
@Table(name="answers")
public class Answere {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idanswere;
	
	@NotNull
	@Size(max=200)
	private String answere;
	
	@NotNull
	private Boolean correct;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	private Question question;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "answere")
	private ImgFileAnswere imgFile;

	public Answere() {
		super();
	}

	public Answere(@NotNull @Size(max = 200) String answere, @NotNull Boolean correct) {
		super();
		this.answere = answere;
		this.correct = correct;
	}	
	
	public String generateBase64Image()
	{
	    return Base64.encodeBase64String(this.imgFile.getData());
	}

	public ImgFileAnswere getImgFile() {
		if(this.imgFile != null) {
			return imgFile;
		}else {
			return null;
		}
	}

	public void setImgFile(ImgFileAnswere imgFile) {
		this.imgFile = imgFile;
	}

	public Integer getIdanswere() {
		return idanswere;
	}

	public void setIdanswere(Integer idanswere) {
		this.idanswere = idanswere;
	}

	public String getAnswere() {
		return answere;
	}
	
	@Secured("ROLE_ADMIN")
	public void setAnswere(String answere) {
		this.answere = answere;
	}

	public Boolean getCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	

}
