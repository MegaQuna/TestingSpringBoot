package com.test.woloszkiewicz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TestQuestionDeliveryAgentPK implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "QUESTION_ID")
    private Integer question_id;
     
 	@Column(name = "TEST_ID")
    private Integer test_id;

	public TestQuestionDeliveryAgentPK() {
		super();
	}

	public TestQuestionDeliveryAgentPK(Integer question_id, Integer test_id) {
		super();
		this.question_id = question_id;
		this.test_id = test_id;
	}

	public Integer getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(Integer question_id) {
		this.question_id = question_id;
	}

	public Integer getTest_id() {
		return test_id;
	}

	public void setTest_id(Integer test_id) {
		this.test_id = test_id;
	}
 	
 	
 	
}
