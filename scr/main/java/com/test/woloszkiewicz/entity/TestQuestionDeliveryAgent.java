package com.test.woloszkiewicz.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
    @NamedQuery(name = "TestQuestionDeliveryAgent.findAllByTest", query = "select u from TestQuestionDeliveryAgent u where u.test = ?1"),
    @NamedQuery(name = "TestQuestionDeliveryAgent.findOneByTestAndNumber", query = "select u from TestQuestionDeliveryAgent u where u.test = ?1 and u.question_number=?2"),
})
@Table(name = "questions_has_tests")
public class TestQuestionDeliveryAgent implements Serializable {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@EmbeddedId
	    private TestQuestionDeliveryAgentPK id;
	 	
	 	 @ManyToOne
	     @MapsId("question_id")
	     @JoinColumn(name = "QUESTION_ID")
	     private Question question;

	     @ManyToOne
	     @MapsId("test_id")
	     @JoinColumn(name = "TEST_ID")
	     private Test test; 
	     
	     private Integer question_number; 
	     
	     @ElementCollection
         private  List<Integer> answere;
	       

		public TestQuestionDeliveryAgent() {
			super();
		}
		

		public TestQuestionDeliveryAgent(Question question, Test test, Integer question_number) {
			super();
			
			this.question = question;
			this.test = test;
			this.question_number = question_number;

		}


		public Question getQuestion() {
			return question;
		}

		public void setQuestion(Question question) {
			this.question = question;
		}

		public Test getTest() {
			return test;
		}

		public void setTest(Test test) {
			this.test = test;
		}

		public Integer getQuestion_number() {
			return question_number;
		}

		public void setQuestion_number(Integer question_number) {
			this.question_number = question_number;
		}


		public List<Integer> getAnswere() {
			return answere;
		}


		public void setAnswere(List<Integer> answere) {
			this.answere = answere;
		}


		public TestQuestionDeliveryAgentPK getId() {
			return id;
		}


		public void setId(TestQuestionDeliveryAgentPK id) {
			this.id = id;
		}     
		
		public Boolean getChceck(Integer Idanswere) {
			if(this.answere.contains(Idanswere)) {
				return true;
			}else {
				return false;
			}
			
		}

}
