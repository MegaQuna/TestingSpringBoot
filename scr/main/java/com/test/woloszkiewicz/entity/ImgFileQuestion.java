package com.test.woloszkiewicz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.tomcat.util.codec.binary.Base64;

@Entity
@NamedQueries({
    @NamedQuery(name="ImgFileQuestion.findOneByid",
                query="select u from ImgFileQuestion u where u.id = ?1"),
})
@Table(name = "FILES_UPLOAD")
public class ImgFileQuestion {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FILE_ID")
    private Integer id;
	
	@Column(name = "FILE_DATA", length=200000)
    private byte[] data;
	
	@Column(name = "FILE_NAME")
    private String file_name;
	
	@OneToOne(fetch=FetchType.LAZY)
	private Question question;

	public ImgFileQuestion() {
		super();
	}
		
	public ImgFileQuestion(byte[] data, String file_name, Question question) {
		super();
		this.data = data;
		this.file_name = file_name;
		this.question = question;
	}



	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String generateBase64Image()
	{
	    return Base64.encodeBase64String(this.getData());
	}
	
    public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public byte[] getData() {
        return data;
    }
 
    public void setData(byte[] data) {
        this.data = data;
    } 
    
}