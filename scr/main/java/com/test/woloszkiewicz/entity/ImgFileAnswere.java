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
    @NamedQuery(name="ImgFileAnswere.findOneByid",
                query="select u from ImgFileAnswere u where u.id = ?1"),
})
@Table(name = "FILES_UPLOAD")
public class ImgFileAnswere {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FILE_ID")
    private Integer id;
	
	@Column(name = "FILE_DATA", length=200000)
    private byte[] data;
	
	@Column(name = "FILE_NAME")
    private String file_name;
	
	@OneToOne(fetch=FetchType.LAZY)
	private Answere answere;

	public ImgFileAnswere() {
		super();
	}
		
	public ImgFileAnswere(byte[] data, String file_name, Answere answere) {
		super();
		this.data = data;
		this.file_name = file_name;
		this.answere = answere;
	}



	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public Answere getAnswere() {
		return answere;
	}

	public void setAnswere(Answere answere) {
		this.answere = answere;
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