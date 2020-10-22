package com.cg.batch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "STUDENT")
public class Student {

	@Id
	@Column(name = "STUDENTID")
	private int studentId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "GENDER")
	private String gender;
	@Column(name = "AGE")
	private int age;
	@Column(name = "QUALIFICATION")
	private String qualification;
}
