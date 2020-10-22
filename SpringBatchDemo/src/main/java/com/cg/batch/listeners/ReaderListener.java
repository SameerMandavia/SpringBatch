package com.cg.batch.listeners;

import org.springframework.batch.core.ItemReadListener;

import com.cg.batch.entity.Student;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReaderListener implements ItemReadListener<Student>{

	@Override
	public void beforeRead() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterRead(Student item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReadError(Exception ex) {
		log.error(ex.getMessage());
	}

}
