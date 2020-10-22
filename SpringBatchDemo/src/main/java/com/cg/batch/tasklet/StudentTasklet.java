package com.cg.batch.tasklet;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.util.CollectionUtils;

import com.cg.batch.entity.Student;

public class StudentTasklet implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		try (Stream<String> studentsRecords = Files.lines(Paths.get("src/main/resources/input/student.csv"))) {
			List<Student> studentList = studentsRecords.map((data) -> data.split(","))
					.map(StudentTasklet::studentMapper).collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(studentList)) {

				Map<String, Long> qualificationGroup = studentList.stream()
						.collect(Collectors.groupingBy(Student::getQualification, Collectors.counting()));
				
				System.out.println(qualificationGroup);

			}
		}

		return RepeatStatus.FINISHED;
	}

	private static Student studentMapper(String records[]) {
		Student student = new Student();
		
		student.setQualification(records[5]);
		return student;
	}

}
