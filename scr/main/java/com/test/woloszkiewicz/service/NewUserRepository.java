package com.test.woloszkiewicz.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.woloszkiewicz.entity.NewUser;

@Repository
public interface NewUserRepository extends JpaRepository<NewUser, Integer> {

}
