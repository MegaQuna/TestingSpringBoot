package com.test.woloszkiewicz.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.woloszkiewicz.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer>{

}
