package com.test.woloszkiewicz.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.woloszkiewicz.entity.ImgFileQuestion;

public interface ImgFileQuestionRepository extends JpaRepository<ImgFileQuestion, Integer> {
	
	public ImgFileQuestion findOneByid(Integer id);	

}
