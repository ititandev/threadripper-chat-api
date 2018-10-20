package org.ititandev.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ititandev.model.Conversation;
import org.ititandev.model.Message;
import org.springframework.jdbc.core.RowMapper;

public class ConversationMapper implements RowMapper<Conversation> {
	public Conversation mapRow(ResultSet rs, int rowNum) throws SQLException {
		Conversation conversation = new Conversation();
		conversation.setConversationName(rs.getString("conversationName"));

		Message lastMessage;
		if (rs.getString("messageId") == null)
			lastMessage = null;
		else {
			lastMessage = new Message();
			lastMessage.setConversationId(rs.getString("conversationId"));
			lastMessage.setMessageId(rs.getString("messageId"));
			lastMessage.setContent(rs.getString("content"));
			lastMessage.setDatetime(rs.getString("datetime"));
			lastMessage.setType(rs.getString("type"));
			lastMessage.setRead(rs.getBoolean("read"));
			lastMessage.setUsername(rs.getString("username"));
		}
		conversation.setLastMessage(lastMessage);

		conversation.setConversationId(rs.getString("conversationId"));
		conversation.setNotiCount(rs.getInt("notiCount"));
		return conversation;
	}
}