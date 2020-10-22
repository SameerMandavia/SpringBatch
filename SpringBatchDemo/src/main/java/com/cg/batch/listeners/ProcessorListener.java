package com.cg.batch.listeners;

import org.springframework.batch.core.ItemProcessListener;

import com.cg.batch.entity.Student;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ProcessorListener implements ItemProcessListener<Student, Student> {

	@Override
	public void beforeProcess(Student item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterProcess(Student item, Student result) {

	}

	@Override
	public void onProcessError(Student item, Exception e) {
		log.error(e.getMessage());
		
	}

}
