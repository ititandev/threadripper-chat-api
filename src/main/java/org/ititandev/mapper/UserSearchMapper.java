package org.ititandev.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ititandev.config.Config;
import org.ititandev.model.UserSearch;
import org.springframework.jdbc.core.RowMapper;

public class UserSearchMapper implements RowMapper<UserSearch> {
	public UserSearch mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserSearch userSearch = new UserSearch();
		userSearch.setUsername(rs.getString("username"));
		userSearch.setDisplayName(rs.getString("displayName"));
		userSearch.setEmail(rs.getString("email"));
		userSearch.setAvatarUrl(rs.getString("avatarUrl"));
		userSearch.setOnline(rs.getBoolean("online"));
		return userSearch;
	}
}