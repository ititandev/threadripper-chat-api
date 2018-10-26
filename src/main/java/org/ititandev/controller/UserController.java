package org.ititandev.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.ititandev.Application;
import org.ititandev.config.Config;
import org.ititandev.dao.UserDAO;
import org.ititandev.model.UserReg;
import org.ititandev.model.UserSearch;
import org.ititandev.security.TokenHandler;
import org.ititandev.service.MailService;
import org.json.JSONException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	static UserDAO userDAO = Application.context.getBean("UserDAO", UserDAO.class);

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	static String verifyBody;
	static String forgotBody;

	static {
		try {
			verifyBody = new String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path"))),
					StandardCharsets.UTF_8);
			forgotBody = new String(Files.readAllBytes(Paths.get(Config.getConfig("mail.forgot.path"))),
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@GetMapping("/")
	public Object root(HttpServletResponse response) throws IOException {
		response.setContentType(MediaType.ALL_VALUE);
		String filename = "threadripper.apk";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		File f = new File(Config.getConfig("apk.file"));
		if (!f.exists())
			return "File not found";
		return new FileSystemResource(Config.getConfig("apk.file"));
	}

	@PostMapping(value = "/api/signup")
	public Object signUp(HttpServletResponse response, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("displayName") String displayName,
			@RequestParam("email") String email) throws IOException, JSONException {
		response.setContentType("application/json");
		int result = 0;
		try {
			String encoded_password = passwordEncoder.encode(password);
			result = userDAO.insert(username, encoded_password, email, displayName, 1);
		} catch (DuplicateKeyException e) {
			response.sendError(409, "Username has been used");
			return null;
		}
		if (result == 1) {
//			String verify = userDAO.getVerifyLink(username);
//			String to = userDAO.getEmail(username);
//			MailService.sendMail(to, "Threadripper: Verify account", verifyBody.replace("{{action_url}}", verify));
			return "{\"success\": true}";
		} else
			response.sendError(520, "Some error has occurred");
		return null;
	}

	@PostMapping(value = "/api/signup2")
	public Object signUp2(HttpServletResponse response, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("displayName") String displayName,
			@RequestParam("email") String email, @RequestParam("avatarUrl") String avatarUrl)
			throws IOException, JSONException {
		response.setContentType("application/json");
		int result = 0;
		try {
			String encoded_password = passwordEncoder.encode(password);
			result = userDAO.insert(username, encoded_password, email, displayName, 1);
			int avatarId = userDAO.insertAvatar(username);
			userDAO.setAvatarFilename(avatarUrl, avatarId);
		} catch (DuplicateKeyException e) {
			response.sendError(409, "Username has been used: " + e.getMessage());
			return null;
		}
		if (result == 1) {
			return "{\"success\": true}";
		} else
			response.sendError(520, "Some error has occurred");
		return null;
	}

	@GetMapping("/api/verify/resend/{username}")
	public Object resend(@PathVariable("username") String username) throws IOException {
		String verify = userDAO.getVerifyLink(username);
		String email = userDAO.getEmail(username);
		MailService.sendMail(email, "Threadripper: Verify account", verifyBody.replace("{{action_url}}", verify));
		return "{\"result\": \"success\"}";
	}

	@GetMapping("/api/verify/{username}/{hash}")
	public String verify(@PathVariable("username") String username, @PathVariable("hash") String hash) {
		return userDAO.verify(username, hash);
	}

	@GetMapping("/api/forgot/{username}")
	public Object forgotPassword(@PathVariable("username") String username) {
		int leftLimit = 97; // letter 'a'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 5;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(targetStringLength);
		for (int i = 0; i < targetStringLength; i++) {
			int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		String newPassword = buffer.toString();
		System.out.println("newpass: " + newPassword);
		userDAO.updatePassword(username, passwordEncoder.encode(newPassword));
		MailService.sendMail(userDAO.getEmail(username), "Threadripper: Forgot password",
				forgotBody.replace("{{newpass}}", newPassword));
		return "{\"result\": \"success\", \"newPassword\": " + newPassword + "}";
	}

	@PutMapping("/api/password")
	public String updatePassword(HttpServletResponse response, @RequestParam("newPassword") String newPassword,
			@RequestParam("oldPassword") String oldPassword) throws JSONException, IOException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		newPassword = passwordEncoder.encode(newPassword);
		if (!passwordEncoder.matches(oldPassword, userDAO.getOldPassword(username))) {
			response.sendError(400, "Incorrect password");
			return null;
		}
		int code = userDAO.updatePassword(username, newPassword);
		if (code == 1) {
			return "{\"result\":\"success\"}";
		} else {
			response.sendError(520, "Some error has occurred");
			return null;
		}
	}

	@PostMapping("/refresh")
	public String refreshToken(HttpServletResponse response, Authentication authentication) throws IOException {
		String username = authentication.getName();
		if (userDAO.checkLock(username)) {
			response.sendError(401, "User is disabled");
			return null;
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
			MailService.sendMail(email, "Threadripper: Verify account", verifyBody.replace("{{action_url}}", verify));
		}
		return check;
	}

	@GetMapping("/api/user")
	public List<UserReg> getUserList() {
		return userDAO.getUserList();
	}

	@GetMapping(value = "/api/user", params = "search")
	public List<UserSearch> searchUser(@RequestParam("search") String keyword) {
		return userDAO.searchUser(keyword, 0, 20);
	}
	@GetMapping(value = "/api/user", params = {"search", "offset", "limit"})
	public List<UserSearch> searchUserLimit(@RequestParam("search") String keyword, @RequestParam("offset") int offset, @RequestParam("limit") int limit) {
		return userDAO.searchUser(keyword, offset, limit);
	}
}
