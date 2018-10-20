package org.ititandev.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.ititandev.model.Message;
import org.springframework.jdbc.core.RowMapper;

public class MessageMapper implements RowMapper<Message> {
	public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
		Message message = new Message();
		message.setMessageId(rs.getString("messageId"));
		message.setType(rs.getString("type"));
		message.setContent(rs.getString("content"));
		message.setDatetime(rs.getString("datetime"));
		message.setConversationId(rs.getString("conversationId"));
		message.setUsername(rs.getString("username"));
		message.setRead(rs.getBoolean("read"));
		return message;
	}
}