package com.cg.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.cg.batch.Writer.StudentWriter;
import com.cg.batch.entity.Student;
import com.cg.batch.listeners.JobListener;
import com.cg.batch.listeners.ProcessorListener;
import com.cg.batch.listeners.ReaderListener;
import com.cg.batch.listeners.WriterListener;
import com.cg.batch.processor.StudentProcessor;
import com.cg.batch.skipPolicy.StudentSkipPolicy;
import com.cg.batch.tasklet.GenderTasklet;
import com.cg.batch.tasklet.StudentTasklet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	public String inputFilePath = "src/main/resources/input/student.csv";
	public String outputFilePath = "src/main/resources/output/student.csv";

	@Bean
	public FlatFileItemReader<Student> studentItemReader() {
		FlatFileItemReader<Student> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(new FileSystemResource(inputFilePath));
		flatFileItemReader.setName("Employee-CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(studentLineMapper());
		log.info("hello");
		return flatFileItemReader;
	}

	@Bean
	public LineMapper<Student> studentLineMapper() {
		DefaultLineMapper<Student> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] { "studentId", "name", "email", "age", "gender", "qualification" });

		BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Student.class);

		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);

		return defaultLineMapper;
	}

	@Bean
	public FlatFileItemWriter<Student> csvWriter() {
		FlatFileItemWriter<Student> writer = new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource(outputFilePath));
		writer.setLineAggregator(new DelimitedLineAggregator<Student>() {
			{
				
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<Student>() {
					{
						setNames(new String[] { "studentId", "name", "email", "age", "gender", "qualification" });
					}
				});
			}
		});
		

		return writer;
	}

	@Bean
	public StudentProcessor studentProcessor() {
		return new StudentProcessor();
	}

	@Bean
	public StudentWriter studentWriter() {
		return new StudentWriter();
	}

	@Bean
	public ReaderListener readerListener() {
		return new ReaderListener();
	}

	@Bean
	public ProcessorListener processorListener() {
		return new ProcessorListener();
	}

	@Bean
	public WriterListener writerListener() {
		return new WriterListener();
	}

	@Bean
	public StudentSkipPolicy studentSkipPolicy() {
		return new StudentSkipPolicy();
	}

	@Bean
	public StudentTasklet studentTasklet() {
		return new StudentTasklet();
	}
	
	@Bean
	public GenderTasklet genderTasklet() {
		return new GenderTasklet();
	}

	@Bean
	public Step stepForCSVToDb() {
		return stepBuilderFactory.get("StepForCSVToDb").<Student, Student>chunk(10).reader(studentItemReader())
				.processor(studentProcessor()).writer(studentWriter()).listener(readerListener())
				.listener(processorListener()).listener(writerListener()).faultTolerant()
				.skipPolicy(studentSkipPolicy()).build();
	}

	@Bean
	public Step stepForDBToCSV() {
		return stepBuilderFactory.get("StepForDBToCSV").<Student, Student>chunk(10).reader(studentItemReader())
				.processor(studentProcessor())
				.writer(csvWriter()).listener(readerListener())
				.listener(processorListener()).listener(writerListener()).faultTolerant()
				.skipPolicy(studentSkipPolicy()).build();
	}

	@Bean
	public Step stepForStudentQualificationTasklet() {
		return stepBuilderFactory.get("stepForStudentQualificationTasklet").tasklet(studentTasklet()).build();
	}
	
	@Bean
	public Step stepForStudentsGenderCount() {
		return stepBuilderFactory.get("stepForStudentsGenderCount").tasklet(genderTasklet()).build();
	}

	@Bean
	public Job exportJob() {
		return jobBuilderFactory.get("Export-Student-Data").incrementer(new RunIdIncrementer())
				.listener(new JobListener())
				.start(stepForCSVToDb())
				.next(stepForDBToCSV())
				.next(stepForStudentQualificationTasklet())
				.next(stepForStudentsGenderCount())
				.build();
	}

}
