package org.ititandev.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ititandev.config.Config;
import org.ititandev.model.UserConversation;
import org.springframework.jdbc.core.RowMapper;

public class UserConversationMapper implements RowMapper<UserConversation> {
	public UserConversation mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserConversation userConversation = new UserConversation();
		userConversation.setUsername(rs.getString("username"));
		userConversation.setDisplayName(rs.getString("displayName"));
		userConversation.setNickname(rs.getString("nickname"));
		userConversation.setAvatarUrl(rs.getString("avatarUrl"));
		userConversation.setOnline(rs.getBoolean("online"));
		return userConversation;
	}
}