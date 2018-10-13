package org.ititandev.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ititandev.Application;
import org.ititandev.config.Config;
import org.ititandev.dao.UserDAO;
import org.ititandev.model.Friend;
import org.ititandev.model.User;
import org.ititandev.security.TokenHandler;
import org.ititandev.service.MailService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	static UserDAO userDAO = Application.context.getBean("UserDAO", UserDAO.class);

	@GetMapping("/")
	public FileSystemResource root(HttpServletResponse response) throws IOException {
		response.setContentType(MediaType.ALL_VALUE);
		String filename = "threadripper.apk";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
//		InputStream inputStream = new FileInputStream(Config.getConfig("apk.dir") + File.separator + filename);
//		BufferedImage img = ImageIO.read(inputStream);
//		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//		ImageIO.write(img, "jpg", byteStream);
//		return byteStream.toByteArray();
		return new FileSystemResource(Config.getConfig("apk.dir") + File.separator + filename); 
	}

	@PostMapping("/api/signup")
	public Object signUp(HttpServletRequest request, HttpServletResponse response, @RequestBody String body)
			throws IOException, JSONException {
		JSONObject json = new JSONObject(body);
		response.setContentType("application/json");
		int result = 0;
		try {
			// BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			result = userDAO.insert(json.getString("username"), json.getString("password"), json.getString("email"),
					json.getString("displayName"));
//			 int result = accountDAO.insert(json.getString("username"),
			// bcrypt.encode(json.getString("password")), json.getString("email"),
			// json.getString("name"));
		} catch (DuplicateKeyException e) {
			response.sendError(409, "User đã tồn tại");
			return null;
		}
		if (result == 1) {
//			String body1 = new String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path1"))),
//					StandardCharsets.UTF_8);
//			String body2 = new String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path2"))),
//					StandardCharsets.UTF_8);
//			String verify = userDAO.getVerifyLink(json.getString("username"));
//			String email = userDAO.getEmail(json.getString("username"));
//			MailService.sendMail(email, "Threadripper: Verify account", body1 + verify + body2);
			return "{\"success\": true}";
		}
		else
			response.sendError(520, "Đã có lỗi xảy ra");
			return null;
	}

	@GetMapping("/api/verify/resend/{username}")
	public Object resend(@PathVariable("username") String username) throws IOException {
		String body1 = new String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path1"))),
				StandardCharsets.UTF_8);
		String body2 = new String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path2"))),
				StandardCharsets.UTF_8);
		String verify = userDAO.getVerifyLink(username);
		String email = userDAO.getEmail(username);
		MailService.sendMail(email, "Instagram: Verify account", body1 + verify + body2);
		return new HashMap<>().put("success", true);
	}

	@GetMapping("/api/verify/{username}/{hash}")
	public String verify(@PathVariable("username") String username, @PathVariable("hash") String hash) {
		return userDAO.verify(username, hash);
	}

	@GetMapping("/api/friends")
	public List<Friend> account() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userDAO.getFriends(username);
	}

	@PutMapping("/api/account/updateInfo")
	public String updateInfo(HttpServletRequest request, @RequestBody String body) throws IOException, JSONException {
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		JSONObject json = new JSONObject(body);
//		Account account = new Account();
//		account.setUsername(json.getString("username"));
//		account.setEmail(json.getString("email"));
//		account.setName(json.getString("name"));
//
//		if (userDAO.updateInfo(username, account) == 1)
//			return "{\"result\":\"success\"}";
//		else
			return "{\"result\":\"error\"}";
	}

	@PutMapping("/api/changePassword")
	public String updatePassword(HttpServletRequest request) throws JSONException, IOException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		StringBuffer buffer = new StringBuffer();
		String line = null;
		BufferedReader reader = request.getReader();
		while ((line = reader.readLine()) != null)
			buffer.append(line);
		JSONObject json = new JSONObject(buffer.toString());
		if (userDAO.updatePassword(username, json.getString("password")) == 1)
			return "{\"result\":\"success\"}";
		else
			return "{\"result\":\"error\"}";
	}

	@PostMapping("/refresh")
	public String refreshToken(HttpServletResponse response, Authentication authentication) throws IOException {
		String username = authentication.getName();
		if (userDAO.checkLock(username)) {
			response.setStatus(401);
			return "{\r\n" + "    \"timestamp\": 1526663411911,\r\n" + "    \"status\": 401,\r\n"
					+ "    \"error\": \"Unauthorized\",\r\n"
					+ "    \"message\": \"Authentication Failed: User is disabled\",\r\n"
					+ "    \"path\": \"/login\"\r\n" + "}";
		}
		TokenHandler tokenHandler = new TokenHandler();
		String JWT = tokenHandler.build(username);
		response.addHeader(tokenHandler.HEADER_STRING, tokenHandler.TOKEN_PREFIX + " " + JWT);
		String check = userDAO.checkVerify(username);
		if (check.equals("false")) {
			String body1 = new String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path1"))),
					StandardCharsets.UTF_8);
			String body2 = new String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path2"))),
					StandardCharsets.UTF_8);
			String verify = userDAO.getVerifyLink(username);
			String email = userDAO.getEmail(username);
			MailService.sendMail(email, "Instagram: Verify account", body1 + verify + body2);
		}
		return check;
	}

	@GetMapping(value = "/search/user", params = "search")
	public List<User> searchUser(@RequestParam("search") String keyword) {
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		return userDAO.searchUser(keyword, currentUser);
	}

	@GetMapping("/api/addFriend/{username}")
	public String follow(@PathVariable("username") String username) {
		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
		return "{\"follow_status\":\"" + userDAO.addFriend(currentUser, username) + "\"}";
	}
}
