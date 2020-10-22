package com.cg.batch.Writer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;

import com.cg.batch.entity.Student;
import com.cg.batch.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentWriter implements ItemWriter<Student> {

	@Autowired
	public StudentRepository studentRepository;

	@Override
	public void write(List<? extends Student> students) throws Exception {
		log.info("Data Saving.....");
		studentRepository.saveAll(students);
		log.info("Data Saved.");

	}

	@Bean()
	public JpaPagingItemReader<Student> dbItemReader(EntityManagerFactory factory) {
		JpaPagingItemReader<Student> crudItemReader = new JpaPagingItemReader<>();
		crudItemReader.setEntityManagerFactory(factory.unwrap(SessionFactory.class));
		crudItemReader.setQueryString("From Student s");
		return crudItemReader;
	}

	/*public class StudentRowMapper implements RowMapper<Student> {

		@Override
		public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
			Student student = new Student();
			student.setStudentId(rs.getInt("STUDENTID"));
			student.setName(rs.getString("NAME"));
			student.setEmail(rs.getString("EMAIL"));
			student.setAge(rs.getInt("AGE"));
			student.setGender(rs.getString("GENDER"));
			student.setQualification(rs.getString("QUALIFICATION"));
			return student;
		}

	}*/

}
