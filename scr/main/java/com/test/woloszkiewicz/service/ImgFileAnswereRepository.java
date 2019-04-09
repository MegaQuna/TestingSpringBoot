package com.test.woloszkiewicz.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.woloszkiewicz.entity.ImgFileAnswere;

public interface ImgFileAnswereRepository extends JpaRepository<ImgFileAnswere, Integer> {
	
	public ImgFileAnswere findOneByid(Integer id);	

}
