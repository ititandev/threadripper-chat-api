package org.ititandev.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.ititandev.Application;
import org.ititandev.config.Config;
import org.ititandev.dao.UserDAO;
import org.ititandev.model.User;
import org.ititandev.service.MailService;

@RestController
public class test {
	static UserDAO userDAO = Application.context.getBean("UserDAO", UserDAO.class);

	@GetMapping("/email")
	public void sendEmail() throws IOException {
		String body1 = new String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path1"))),
				StandardCharsets.UTF_8);
		String body2 = new String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path2"))),
				StandardCharsets.UTF_8);
		String verify = "test";
		String email = "manhpcpro@gmail.com";
		MailService.sendMail(email, "Threadripper: Verify account", body1 + verify + body2);
	}

	@GetMapping("/userlist")
	public List<User> test() {
		return userDAO.getAll();
	}

	@GetMapping("/current")
	public String current() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@PostMapping("/test/{username1}/{username2}")
	public Map<String, Object> request(@PathVariable("username1") String username1,
			@PathVariable("username2") String username2) {
		return null;
	}

	@PostMapping("/upload")
	Object uploadFileHandler(@RequestParam("file") MultipartFile file) {
		String filename = "test.jpg";
		try {
			byte[] bytes = file.getBytes();
			File dir = new File(Config.getConfig("photo.dir"));
			if (!dir.exists())
				dir.mkdirs();
			File serverFile = new File(dir.getAbsolutePath() + File.separator + filename);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
			System.out.println("[ITitan] Upload PHOTO SUCCESS file: " + serverFile.getAbsolutePath());
			return "{\"result\":\"success\"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"result\":\"error\"}";
		}
	}
}
