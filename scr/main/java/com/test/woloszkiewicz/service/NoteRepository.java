package com.test.woloszkiewicz.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.woloszkiewicz.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
	
}
