package com.devusperior.hello_world_spring_batch.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.devusperior.hello_world_spring_batch.tasklet.PrintHelloTasklet;

@Configuration
public class PrintHelloStepConfig {

	@Bean
	public Step printHelloStep(JobRepository jobRepository, PlatformTransactionManager transactionalManager,
			PrintHelloTasklet printHelloTasklet) {
		return new StepBuilder("printHelloStep", jobRepository)
				.tasklet(printHelloTasklet, transactionalManager)
				.build();
	}
}
