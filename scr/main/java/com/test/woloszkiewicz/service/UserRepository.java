package com.test.woloszkiewicz.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.woloszkiewicz.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findOneByUsername(String username);
	
	public User findOneByid(Integer idusername);
	
	public Integer existUsername(String username);
	
	public Integer existEmail(String email);

}
