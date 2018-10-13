package org.ititandev.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.ititandev.model.Friend;
import org.springframework.jdbc.core.RowMapper;

public class FriendMapper implements RowMapper<Friend>{
	public Friend mapRow(ResultSet rs, int rowNum) throws SQLException {
		Friend friend = new Friend();
		friend.setDisplayName(rs.getString("displayName"));
		friend.setAvatarUrl(rs.getString("avatarUrl"));
		friend.setLastMessage(null);
		friend.setConversationId(rs.getString("conversationId"));
		friend.setOnline(rs.getBoolean("online"));
		return friend;
	}
}