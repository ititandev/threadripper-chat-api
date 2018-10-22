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
import org.ititandev.model.UserReg;
import org.ititandev.service.MailService;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

@RestController
public class test {
	static UserDAO userDAO = Application.context.getBean("UserDAO", UserDAO.class);

	@GetMapping("/email")
	public void testMail() throws IOException {

		String body1 = new String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path"))),
				StandardCharsets.UTF_8);
		String verify = "test";

		Email email = EmailBuilder.startingBlank()
				.from("Threadripper Chat System", Config.getConfig("mail.username") + "@gmail.com")
				.to("User", "manhpcpro@gmail.com").withSubject("hey")
				.withHTMLText(body1.replace("{{action_url}}", "http://localhost:8083/index.html"))
//				.withHTMLText("<h1>Verify your account</h1><a href=\"https://www.w3schools.com/html/\">Click to verify</a>")
				.buildEmail();

		Mailer mailer = MailerBuilder
				.withSMTPServer("smtp.gmail.com", 587, Config.getConfig("mail.username"),
						Config.getConfig("mail.password"))
				.withTransportStrategy(TransportStrategy.SMTP_TLS).buildMailer();

		mailer.sendMail(email);

		// new Mailer("smtp.gmail.com", 25, Config.getConfig("mail.username"),
		// Config.getConfig("mail.password")).withTransportStrategy(TransportStrategy.SMTP_TLS).sendMail(email);
		// new Mailer("smtp.gmail.com", 587, "your user", "your
		// password").sendMail(email);
		// new Mailer("smtp.gmail.com", 465, "your user", "your password",
		// TransportStrategy.SMTP_SSL).sendMail(email);

		// String body1 = new
		// String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path1"))),
		// StandardCharsets.UTF_8);
		// String body2 = new
		// String(Files.readAllBytes(Paths.get(Config.getConfig("mail.verify.path2"))),
		// StandardCharsets.UTF_8);
		// String verify = "test";
		// String email = "manhpcpro@gmail.com";
		// MailService.sendMail(email, "Threadripper: Verify account", body1 + verify +
		// body2);
	}

	@GetMapping("/userlist")
	public List<UserReg> test() {
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
