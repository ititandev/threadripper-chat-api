package org.ititandev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.sql.DataSource;
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

	public String addConversation(List<String> usernames) {
		String sql = "SELECT COUNT(1) AS result, conversationId FROM "
				+ "(SELECT conversationId, GROUP_CONCAT( username ORDER BY username SEPARATOR ',') AS list_user "
				+ "FROM threadripper.conversation GROUP BY conversationId) AS list_conv "
				+ "WHERE list_conv.list_user = ?";
		Collections.sort(usernames);
		String list_user = String.join(",", usernames);
		System.out.println(sql + list_user);
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

		// HashSet<>(list1).equals(new HashSet<>(list2))
	}

}
