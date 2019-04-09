package com.test.woloszkiewicz.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.woloszkiewicz.entity.Testsetting;

@Repository
public interface TestSettingsRepository extends JpaRepository<Testsetting, Integer>{
	
	public List<Testsetting> findAllOrderByDate_of_test();
	
	public Testsetting findOneById(Integer idtest);

}
