package org.ititandev.dao;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.DataSource;

import org.ititandev.config.Config;
import org.ititandev.model.Friend;
import org.ititandev.model.User;
import org.ititandev.model.UserSearch;
import org.ititandev.mapper.FriendMapper;
import org.ititandev.mapper.UserMapper;
import org.ititandev.mapper.UserSearchMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class UserDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<User> getAll() {
		String query = "SELECT * from user";
		List<User> empList = jdbcTemplate.query(query, new UserMapper());
		return empList;
	}

	public int insert(String username, String password, String email, String displayName) {
		String sql = "INSERT INTO user VALUES (?, ?, ?, 1, 0, NOW(), NOW(), ?, ?, 0)";
		Random rand = new Random();
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String hash = encoder.encodePassword(String.valueOf(rand.nextInt(1000000)), "hash");
		return jdbcTemplate.update(sql, username, password, email, displayName, hash);
	}

	public int updateInfo(String username, User user) {
		String sql = "UPDATE user SET username = ?, email = ?, datetime_update = NOW(), name = ? "
				+ "WHERE username = ?";
		return jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getDisplayName(), username);
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
		String sql = "SELECT COUNT(*) AS count FROM user WHERE username = ? AND verify_code = ?";
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
		return Config.getConfig("hostname") + "/api/verify/" + username + "/" + result.get("verify_code");
	}

	public String getEmail(String username) {
		String sql = "SELECT email FROM user WHERE username = ?";
		return jdbcTemplate.queryForList(sql, username).get(0).get("email").toString();
	}

	public List<UserSearch> searchUser(String keyword) {
		String sql = "SELECT user.username, displayName, email," + "get_avatar(user.username) AS avatarUrl "
				+ "FROM user WHERE (user.username LIKE '%" + keyword + "%' OR " + "email LIKE '%" + keyword
				+ "%' OR displayName LIKE '%" + keyword + "%') LIMIT 0, 20";
		return jdbcTemplate.query(sql, new Object[] {}, new UserSearchMapper());
	}

	public Boolean addFriend(String currentUser, String username) {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("set_follow");
		SqlParameterSource in = new MapSqlParameterSource().addValue("user1", currentUser).addValue("user2", username);
		Map<String, Object> out = jdbcCall.execute(in);
		return Boolean.valueOf(out.get("output").toString());
	}

	public List<Friend> getFriends(String username) {
		String sql = "SELECT * FROM conversation WHERE username1 = ? OR username2 = ?";
		return jdbcTemplate.query(sql, new Object[] { username }, new FriendMapper());
	}

}
