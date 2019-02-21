package com.example.task;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.example.task.SqlExecutingTaskApplication;

@RunWith(SpringRunner.class)
public class SqlExecutingTaskApplicationTests {

	private static final String TARGET_DB_URL = "jdbc:h2:mem:targetdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
	private static final String TARGET_DB_USERNAME = "sa";
	private static final String TARGET_DB_PASSWORD = "notused";
	
	@Test
	public void shouldExecuteScriptAgainstDb() throws Exception {
	
		SpringApplication.run(SqlExecutingTaskApplication.class, 
			"--sqlscript=classpath:/scripts/test.sql",
			"--target.datasource.url=" + TARGET_DB_URL,
	    	"--target.datasource.username=" + TARGET_DB_USERNAME,
	    	"--target.datasource.password=" + TARGET_DB_PASSWORD);
		
		JdbcTemplate targetJdbcTemplate = getTargetJdbcTemplate();
		assertEquals("items count", 5, JdbcTestUtils.countRowsInTable(targetJdbcTemplate, "test_table"));
		Map<String, Object> document = targetJdbcTemplate.queryForMap("select * from test_table where id = 1");
		assertEquals("test", document.get("name"));
		assertEquals(6, document.get("type"));
	}
	
	private JdbcTemplate getTargetJdbcTemplate() {		   
		
		return new JdbcTemplate(DataSourceBuilder
	        .create()
	        .username(TARGET_DB_USERNAME)
	        .password(TARGET_DB_PASSWORD)
	        .url(TARGET_DB_URL)
	        .build());		
	}
}