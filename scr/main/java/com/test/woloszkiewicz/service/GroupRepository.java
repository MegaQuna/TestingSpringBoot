package com.test.woloszkiewicz.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.woloszkiewicz.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
	
	public Group findOneByid(Integer idgroup);

}
