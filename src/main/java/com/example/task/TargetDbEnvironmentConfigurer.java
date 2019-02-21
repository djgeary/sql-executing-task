package com.example.task;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Determine target DB type from JDBC URL and set target.datasource.driverClassName automatically.
 * This avoids having to set it manually if its different from the task repository one.
 */
public class TargetDbEnvironmentConfigurer implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

	@Override
	public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
		ConfigurableEnvironment environment = event.getEnvironment();
		String targetJdbcUrl = environment.resolvePlaceholders("${target.datasource.url:${spring.datasource.url}}");
		System.setProperty("target.datasource.driverClassName", DatabaseDriver.fromJdbcUrl(targetJdbcUrl).getDriverClassName());	
	}
}