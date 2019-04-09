package com.test.woloszkiewicz.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.woloszkiewicz.entity.Loginfail;

@Repository
public interface LoginFailRepository extends JpaRepository<Loginfail, Integer> {

}
