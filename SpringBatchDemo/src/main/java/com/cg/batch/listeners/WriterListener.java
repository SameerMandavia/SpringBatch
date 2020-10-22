package com.cg.batch.listeners;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;

import com.cg.batch.entity.Student;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class WriterListener implements ItemWriteListener<Student>{

	@Override
	public void beforeWrite(List<? extends Student> items) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterWrite(List<? extends Student> items) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWriteError(Exception e, List<? extends Student> items) {
		log.error(e.getMessage());
		
	}

}
