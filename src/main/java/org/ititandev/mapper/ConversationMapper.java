package org.ititandev.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ititandev.model.Conversation;
import org.springframework.jdbc.core.RowMapper;

public class ConversationMapper implements RowMapper<Conversation>{
	public Conversation mapRow(ResultSet rs, int rowNum) throws SQLException {
		Conversation conversation = new Conversation();
//		conversation.setDisplayName(rs.getString("displayName"));
//		conversation.setAvatarUrl(rs.getString("avatarUrl"));
		conversation.setLastMessage(null);
		conversation.setConversationId(rs.getString("conversationId"));
		conversation.setOnline(rs.getBoolean("online"));
		return conversation;
	}
}