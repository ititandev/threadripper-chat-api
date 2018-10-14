package org.ititandev.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@GetMapping("/")
	public FileSystemResource root(HttpServletResponse response) throws IOException {
		response.setContentType(MediaType.ALL_VALUE);
		String filename = "threadripper.apk";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		// InputStream inputStream = new FileInputStream(Config.getConfig("apk.dir") +
		// File.separator + filename);
		// BufferedImage img = ImageIO.read(inputStream);
		// ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		// ImageIO.write(img, "jpg", byteStream);
		// return byteStream.toByteArray();
		return new FileSystemResource(Config.getConfig("apk.dir") + File.separator + filename);
	}

	@PostMapping("/api/signup")
	public Object signUp(HttpServletRequest request, HttpServletResponse response, @RequestBody String body)
			throws IOException, JSONException {
		JSONObject json = new JSONObject(body);
		response.setContentType("application/json");
		int result = 0;
		try {
			String password = passwordEncoder.encode(json.getString("password"));
			result = userDAO.insert(json.getString("username"), password, json.getString("email"),
					json.getString("displayName"));
		} catch (DuplicateKeyException e) {
			response.sendError(409, "User đã tồn tại");
			return null;
		}
		if (result == 1) {
			// String body1 = new
			// String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path1"))),
			// StandardCharsets.UTF_8);
			// String body2 = new
			// String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path2"))),
			// StandardCharsets.UTF_8);
			// String verify = userDAO.getVerifyLink(json.getString("username"));
			// String email = userDAO.getEmail(json.getString("username"));
			// MailService.sendMail(email, "Threadripper: Verify account", body1 + verify +
			// body2);
			return "{\"success\": true}";
		} else
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

	@PutMapping("/api/verify/{username}/{hash}")
	public String verify(@PathVariable("username") String username, @PathVariable("hash") String hash) {
		return userDAO.verify(username, hash);
	}
	
	@PutMapping("/api/forgotPassword/{username}") 
	public void forgotPassword(@PathVariable("username") String username){
//		int leftLimit = 97; // letter 'a'
//	    int rightLimit = 122; // letter 'z'
//	    int targetStringLength = 5;
//	    Random random = new Random();
//	    StringBuilder buffer = new StringBuilder(targetStringLength);
//	    for (int i = 0; i < targetStringLength; i++) {
//	        int randomLimitedInt = leftLimit + (int) 
//	          (random.nextFloat() * (rightLimit - leftLimit + 1));
//	        buffer.append((char) randomLimitedInt);
//	    }
//	    String generatedString = buffer.toString();
//	    
//	    return generatedString;
		userDAO.updatePassword(username, passwordEncoder.encode(username));
	}

	@GetMapping("/api/friends")
	public List<Friend> account() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userDAO.getFriends(username);
	}

	@PutMapping("/api/changePassword")
	public String updatePassword(HttpServletResponse response, @RequestBody String body)
			throws JSONException, IOException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		JSONObject json = new JSONObject(body);
		String newPassword = passwordEncoder.encode(json.getString("newPassword"));
		if (!passwordEncoder.matches(json.getString("oldPassword"), userDAO.getOldPassword(username))) {
			response.sendError(400, "Mật khẩu cũ sai");
			return null;
		}
		int code = userDAO.updatePassword(username, newPassword);
		if (code == 1) {
			return "{\"result\":\"success\"}";
		} else {
			response.sendError(520, "Đã có lỗi xảy ra");
			return null;
		}
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
