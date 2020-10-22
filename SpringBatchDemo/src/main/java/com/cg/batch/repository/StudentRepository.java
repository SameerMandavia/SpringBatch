package com.cg.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.batch.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

}
