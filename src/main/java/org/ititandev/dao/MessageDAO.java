package org.ititandev.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class MessageDAO {
	@SuppressWarnings("unused")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
}
