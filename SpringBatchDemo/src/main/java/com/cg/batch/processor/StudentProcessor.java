package com.cg.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.cg.batch.entity.Student;

public class StudentProcessor implements ItemProcessor<Student, Student> {
	@Override
	public Student process(Student student) throws Exception {

		String names = student.getName().toUpperCase();
		student.setName(names);

		String genders = student.getGender().toUpperCase();
		student.setGender(genders);

		return student;
	}

}
