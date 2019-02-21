package com.example.task;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.task.configuration.DefaultTaskConfigurer;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.cloud.task.configuration.TaskConfigurer;
import org.springframework.cloud.task.repository.support.TaskRepositoryInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;

@SpringBootApplication
@EnableTask
public class SqlExecutingTaskApplication {
	
	@Value("${sqlscript}")
	private Resource sqlScript;

	public static void main(String[] args) {
		SpringApplication.run(SqlExecutingTaskApplication.class, args);
	}
	
	@Bean
	public SqlScriptExecutor sqlScriptExecutor() {
		return new SqlScriptExecutor(sqlScript, targetDataSource());
	}
	
	@Bean
	@Primary
	public DataSourceProperties taskDataSourceProperties() {
	    return new DataSourceProperties();
	}

	@Bean
	@Primary
	public DataSource taskDataSource() {
	    return taskDataSourceProperties().initializeDataSourceBuilder().build();
	}
	
	@Bean
	@ConfigurationProperties("target.datasource")
	public DataSourceProperties dataSourceProperties() {
	    return new DataSourceProperties();
	}

	@Bean
	@ConfigurationProperties("target.datasource")
	public DataSource targetDataSource() {
	    return dataSourceProperties().initializeDataSourceBuilder().build();
	}
	
	// This wires the correct datasource for the task repository
	@Bean
	public TaskConfigurer taskConfigurer() {
	    return new DefaultTaskConfigurer(taskDataSource());
	}
	
	// Only need this for the tests?
	@Bean
	public TaskRepositoryInitializer taskRepositoryInitializer() {
		TaskRepositoryInitializer taskRepositoryInitializer = new TaskRepositoryInitializer();
		taskRepositoryInitializer.setDataSource(taskDataSource());
		return taskRepositoryInitializer;
	}	
}
