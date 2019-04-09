package com.test.woloszkiewicz.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.woloszkiewicz.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

}
