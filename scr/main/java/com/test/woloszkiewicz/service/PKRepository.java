package com.test.woloszkiewicz.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.woloszkiewicz.entity.Test;
import com.test.woloszkiewicz.entity.TestQuestionDeliveryAgent;

@Repository
public interface PKRepository extends JpaRepository<TestQuestionDeliveryAgent, Long>{
	
	public List<TestQuestionDeliveryAgent> findAllByTest(Test test);
	
	public TestQuestionDeliveryAgent findOneByTestAndNumber(Test test, Integer Questionnumber);
}
