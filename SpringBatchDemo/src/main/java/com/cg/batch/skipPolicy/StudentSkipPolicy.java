package com.cg.batch.skipPolicy;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;

public class StudentSkipPolicy implements SkipPolicy{

	@Override
	public boolean shouldSkip(Throwable t, int failedCount) throws SkipLimitExceededException {
		// TODO Auto-generated method stub
		return true;
	}

}
