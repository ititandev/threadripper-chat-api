package org.ititandev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.ititandev.mapper.ConversationMapper;
import org.ititandev.mapper.MessageMapper;
import org.ititandev.mapper.StringMapper;
import org.ititandev.mapper.UserConversationMapper;
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

	public Conversation getConversationWithId(String conversationId) {
		String sql = "SELECT DISTINCT conversation.conversationId, conversationName, "
				+ "getNotiCount(conversation.conversationId) AS notiCount, messageId, `type`, "
				+ "content, `datetime`, mes.username, `read` FROM conversation INNER JOIN conversation_setting LEFT JOIN "
				+ "(SELECT conversationId, messageId, content, `type`, username, `datetime`, `read` "
				+ "FROM message WHERE messageId IN (SELECT MAX(messageId) as id FROM message GROUP BY conversationId)) AS mes "
				+ "ON conversation.conversationId = mes.conversationId WHERE conversation.conversationId = ?";
		List<Conversation> list = jdbcTemplate.query(sql, new Object[] { conversationId }, new ConversationMapper());
		list.stream().forEach(c -> c.setListUser(getUserOfConversation(c.getConversationId())));
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	public int insertImage(String username) {

		String sql = "INSERT INTO image () VALUES ()";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] { "imageId" });
				return ps;
			}
		}, keyHolder);
		return Integer.valueOf(keyHolder.getKey().toString());

	}

	public int setImageFilename(String filename, int imageId) {
		String sql = "UPDATE image SET filename = ? WHERE imageId = ?";
		return jdbcTemplate.update(sql, filename, imageId);
	}

	public int insertFile(String username) {
		String sql = "INSERT INTO file () VALUES ()";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] { "fileId" });
				return ps;
			}
		}, keyHolder);
		return Integer.valueOf(keyHolder.getKey().toString());
	}

	public int setFileFilename(String filename, int fileId) {
		String sql = "UPDATE file SET filename = ? WHERE fileId = ?";
		return jdbcTemplate.update(sql, filename, fileId);
	}

	public List<String> getRevUserJoin(String username, int online) {
		String sql = "UPDATE `user` SET `online` = ? WHERE username = ?";
		jdbcTemplate.update(sql, online, username);

		sql = "SELECT DISTINCT username FROM conversation WHERE conversationId IN "
				+ "(SELECT DISTINCT conversationId FROM conversation WHERE username = ?)";
		return jdbcTemplate.query(sql, new Object[] { username }, new StringMapper());
	}

	public List<String> getRevUser(String username, String conversationId) {
		if (!checkConversationId(username, conversationId))
			return new ArrayList<String>();
		String sql = "SELECT DISTINCT username FROM conversation WHERE conversationId = ?";
		return jdbcTemplate.query(sql, new Object[] { conversationId }, new StringMapper());
	}

	public Message insertMessage(Message mes) {
		Date currentTime = new Date();
		String sql = "INSERT INTO message (`conversationId`,`username`,`type`,`content`, `datetime`) " + 
					"VALUES (?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String datetime = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss").format(currentTime);

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] { "messageId", "datetime" });
				ps.setString(1, mes.getConversationId());
				ps.setString(2, mes.getUsername());
				ps.setString(3, mes.getToken());
				ps.setString(4, mes.getContent());
				ps.setString(5, datetime);
				return ps;
			}
		}, keyHolder);
		mes.setMessageId(keyHolder.getKey().toString());
		mes.setDatetime(datetime);
		return mes;
	}

}
