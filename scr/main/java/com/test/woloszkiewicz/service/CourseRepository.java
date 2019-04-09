package com.test.woloszkiewicz.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.woloszkiewicz.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	public Course findOneByid(Integer idcourse);
	
	public List<Course> findAllByArchive(Boolean archive);

}
