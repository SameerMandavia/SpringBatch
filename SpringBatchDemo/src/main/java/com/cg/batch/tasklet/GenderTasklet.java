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

public class GenderTasklet implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {

		try (Stream<String> studentsRecords = Files.lines(Paths.get("src/main/resources/input/student.csv"))) {
			List<Student> studentList = studentsRecords.map((data) -> data.split(","))
					.map(GenderTasklet::studentsGenderMapper).collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(studentList)) {

				Map<String, Long> genderGroup = studentList.stream()
						.collect(Collectors.groupingBy(Student::getGender, Collectors.counting()));

				System.out.println(genderGroup);

			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}

		return RepeatStatus.FINISHED;

	}

	private static Student studentsGenderMapper(String records[]) {
		Student student = new Student();

		student.setGender(records[4]);

		return student;
	}

}
