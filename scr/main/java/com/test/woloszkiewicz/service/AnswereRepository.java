package com.test.woloszkiewicz.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.woloszkiewicz.entity.Answere;

@Repository
public interface AnswereRepository extends JpaRepository<Answere, Integer> {
	
	public Answere findOneById(Integer id);

}
