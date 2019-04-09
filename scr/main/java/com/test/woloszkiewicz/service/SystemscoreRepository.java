package com.test.woloszkiewicz.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.woloszkiewicz.entity.Systemscore;

@Repository
public interface SystemscoreRepository extends JpaRepository<Systemscore, Integer> {
	
	public Systemscore findOneById(Integer idsystemscore);

}
