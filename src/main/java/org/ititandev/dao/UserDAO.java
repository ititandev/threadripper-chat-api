package org.ititandev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import org.ititandev.config.Config;
import org.ititandev.model.Conversation;
import org.ititandev.model.UserReg;
import org.ititandev.model.UserSearch;
import org.ititandev.mapper.ConversationMapper;
import org.ititandev.mapper.UserMapper;
import org.ititandev.mapper.UserSearchMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class UserDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<UserReg> getAll() {
		String query = "SELECT * from user";
		List<UserReg> empList = jdbcTemplate.query(query, new UserMapper());
		return empList;
	}

	public Map<String, Object> getCurrentUserInfo(String username) {
		String sql = "SELECT displayName, getAvatar(username) as avatarUrl, username, email, active FROM user WHERE username = ?";
		return jdbcTemplate.queryForList(sql, username).get(0);
	}

	public String getDisplayName(String username) {
		String sql = "SELECT displayName FROM user WHERE username = ?";
		return jdbcTemplate.queryForList(sql, username).get(0).get("displayName").toString();
	}

	public int insert(String username, String password, String email, String displayName, int active) {
		String sql = "INSERT INTO user VALUES (?, ?, ?, ?, 0, NOW(), NOW(), ?, ?, 0)";
		Random rand = new Random();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String hash = encoder.encodePassword(String.valueOf(rand.nextInt(1000000)), "hash");
		return jdbcTemplate.update(sql, username, password, email, active, displayName, hash);
	}

	public int updateInfo(String username, UserReg userReg) {
		String sql = "UPDATE user SET username = ?, email = ?, datetime_update = NOW(), name = ? "
				+ "WHERE username = ?";
		return jdbcTemplate.update(sql, userReg.getUsername(), userReg.getEmail(), userReg.getDisplayName(), username);
	}

	public String getOldPassword(String username) {
		String sql = "SELECT password FROM user WHERE username = ?";
		return jdbcTemplate.queryForList(sql, username).get(0).get("password").toString();
	}

	public int updatePassword(String username, String newPassword) {
		String sql = "UPDATE user SET password = ? WHERE username = ?";
		return jdbcTemplate.update(sql, newPassword, username);
	}

	public boolean checkBlock(String username1, String username2) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("check_block_proc");
		SqlParameterSource in = new MapSqlParameterSource().addValue("username1", username1).addValue("username2",
				username2);
		Map<String, Object> out = jdbcCall.execute(in);
		return Boolean.valueOf(out.get("output").toString());
	}

	public String checkVerify(String username) {
		String sql = "SELECT active FROM user WHERE username = ?";
		return jdbcTemplate.queryForList(sql, username).get(0).get("active").toString();
	}

	public boolean checkLock(String username) {
		String sql = "SELECT `lock` FROM user WHERE username = ?";
		return Boolean.valueOf(jdbcTemplate.queryForList(sql, username).get(0).get("lock").toString());
	}

	public String verify(String username, String hash) {
		String sql = "SELECT COUNT(1) AS count FROM user WHERE username = ? AND hash = ?";
		int count = Integer.valueOf(jdbcTemplate.queryForList(sql, username, hash).get(0).get("count").toString());
		if (count == 1) {
			String sql2 = "UPDATE user SET active = 1 WHERE username = ?";
			int row = jdbcTemplate.update(sql2, username);
			if (row == 1)
				return "Verify successfully";
		}
		return "Verify unsuccessfully";
	}

	public String getVerifyLink(String username) {
		String sql = "SELECT hash FROM user WHERE username = ?";
		Map<String, Object> result = jdbcTemplate.queryForList(sql, username).get(0);
		return Config.getConfig("hostname") + "/api/verify/" + username + "/" + result.get("hash");
	}

	public String getEmail(String username) {
		String sql = "SELECT email FROM user WHERE username = ?";
		return jdbcTemplate.queryForList(sql, username).get(0).get("email").toString();
	}

	public List<UserSearch> searchUser(String keyword, int offset, int limit) {
		String sql = "SELECT user.username, displayName, email, getAvatar(user.username) AS avatarUrl, online "
				+ "FROM user WHERE (user.username LIKE '%" + keyword + "%' OR " + "email LIKE '%" + keyword
				+ "%' OR displayName LIKE '%" + keyword + "%') LIMIT ?, ?";
		return jdbcTemplate.query(sql, new Object[] { offset, limit }, new UserSearchMapper());
	}

	public List<UserSearch> getUserByUsername(String username) {
		String sql = "SELECT user.username, displayName, email, getAvatar(user.username) AS avatarUrl, online "
				+ "FROM user WHERE user.username = ?";
		return jdbcTemplate.query(sql, new Object[] { username }, new UserSearchMapper());
	}

	public List<UserReg> getUserList() {
		String sql = "SELECT * FROM user";
		return jdbcTemplate.query(sql, new Object[] {}, new UserMapper());
	}

	public Integer insertAvatar(String username) {
		String sql = "INSERT INTO avatar (username, datetime) VALUES (?, NOW())";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] { "avatar_id" });
				ps.setString(1, username);
				return ps;
			}
		}, keyHolder);
		return Integer.valueOf(keyHolder.getKey().toString());
	}

	public int setAvatarFilename(String avatarUrl, int avatarId) {
		String sql = "UPDATE avatar SET avatarUrl = ? WHERE avatar_id = ?";
		return jdbcTemplate.update(sql, avatarUrl, avatarId);
	}

	public int updateDisplayName(String username, String newDisplayName) {
		String sql = "UPDATE user SET displayName = ? WHERE username = ?";
		return jdbcTemplate.update(sql, newDisplayName, username);
	}

}
