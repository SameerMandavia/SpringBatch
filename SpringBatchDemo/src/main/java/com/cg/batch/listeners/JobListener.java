package com.cg.batch.listeners;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobListener implements JobExecutionListener{

	@Override
	public void beforeJob(JobExecution jobExecution) {
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("BATCH SUCCESSFULLY COMPLETED");
		}
		else {
			log.error("BATCH FAILED");
		}
	}

}
