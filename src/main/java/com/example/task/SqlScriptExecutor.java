package com.example.task;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

/**
 * Executes the specified SQL script against the specified DataSource on
 * application startup.
 */
public class SqlScriptExecutor implements CommandLineRunner {

	private final Resource sqlScript;
	private final DataSource dataSource;
	
	public SqlScriptExecutor(Resource sqlScript, DataSource dataSource) {
		this.sqlScript = sqlScript;
		this.dataSource = dataSource;
	}

	@Override
	public void run(String... args) throws Exception {
		ScriptUtils.executeSqlScript(dataSource.getConnection(), sqlScript);
	}
}
