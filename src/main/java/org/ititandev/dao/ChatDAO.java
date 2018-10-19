package org.ititandev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.ititandev.mapper.ConversationMapper;
import org.ititandev.mapper.MessageMapper;
import org.ititandev.mapper.UserConversationMapper;
import org.ititandev.mapper.UserSearchMapper;
import org.ititandev.model.Conversation;
import org.ititandev.model.Message;
import org.ititandev.model.UserConversation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class ChatDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Conversation> getConversation(String username) {
		String sql = "SELECT DISTINCT conversation.conversationId, conversationName, "
				+ "getNotiCount(conversation.conversationId) AS notiCount, messageId, `type`, "
				+ "content, `datetime`, mes.username, `read` FROM conversation INNER JOIN conversation_setting LEFT JOIN "
				+ "(SELECT conversationId, messageId, content, `type`, username, `datetime`, `read` "
				+ "FROM message WHERE messageId IN (SELECT MAX(messageId) as id FROM message GROUP BY conversationId)) AS mes "
				+ "ON conversation.conversationId = mes.conversationId WHERE conversation.username = ?";
		List<Conversation> list = jdbcTemplate.query(sql, new Object[] { username }, new ConversationMapper());
		list.stream().forEach(c -> c.setListUser(getUserOfConversation(c.getConversationId())));

		return list;
	}

	private List<UserConversation> getUserOfConversation(String conversationId) {
		String sql = "SELECT conversation.username, displayName, nickname, "
				+ "getAvatar(conversation.username) AS avatarUrl, `online` "
				+ "FROM conversation LEFT JOIN `user` ON conversation.username = `user`.username "
				+ "WHERE conversationId = ?";

		return jdbcTemplate.query(sql, new Object[] { conversationId }, new UserConversationMapper());
	}

	public String addConversation(List<String> usernames) {
		String sql = "SELECT COUNT(1) AS result, conversationId FROM "
				+ "(SELECT conversationId, GROUP_CONCAT( username ORDER BY username SEPARATOR ',') AS list_user "
				+ "FROM threadripper.conversation GROUP BY conversationId) AS list_conv "
				+ "WHERE list_conv.list_user = ?";
		Collections.sort(usernames);
		String list_user = String.join(",", usernames);
		Map<String, Object> result = jdbcTemplate.queryForList(sql, list_user).get(0);
		if (Integer.valueOf(result.get("result").toString()) > 0)
			return result.get("conversationId").toString();

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(
						"INSERT INTO conversation_setting  (conversation_setting.conversationName) VALUES (null)",
						new String[] { "conversationId" });
				return ps;
			}
		}, keyHolder);
		String conversationId = keyHolder.getKey().toString();

		sql = "INSERT INTO conversation (conversationId, username) VALUES " + String.join(", ",
				usernames.stream().map(s -> "(" + conversationId + ", '" + s + "')").collect(Collectors.toList()));

		if (jdbcTemplate.update(sql) > 1)
			return conversationId;
		else
			return "0";
	}

	public List<Message> getMessage(String conversationId) {
		String sql = "SELECT * FROM message WHERE conversationId = ? ORDER BY messageId DESC";
		return jdbcTemplate.query(sql, new Object[] { conversationId }, new MessageMapper());
	}

	public boolean checkConversationId(String username, String conversationId) {
		String sql = "SELECT COUNT(*) AS count FROM conversation WHERE conversationId = ? AND username = ?";
		return jdbcTemplate.queryForList(sql, conversationId, username).get(0).get("count").toString().equals("1");
	}

}
