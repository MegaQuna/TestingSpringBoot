package com.test.woloszkiewicz.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.woloszkiewicz.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	public List<Question> findBySingleAndDificulty(Boolean single, Integer dificulty);
	
	public Question findOneById(Integer id);

}
