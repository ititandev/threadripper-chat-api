package org.ititandev.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ititandev.model.UserReg;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<UserReg>{
	public UserReg mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserReg userReg = new UserReg();
		userReg.setUsername(rs.getString("username"));
		userReg.setPassword(rs.getString("password"));
		userReg.setDisplayName(rs.getString("displayName"));
		userReg.setEmail(rs.getString("email"));
		return userReg;
	}
}