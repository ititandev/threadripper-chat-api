package org.ititandev.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.ititandev.Application;
import org.ititandev.config.Config;
import org.ititandev.dao.ChatDAO;
import org.ititandev.dao.ImageDAO;
import org.ititandev.dao.UserDAO;
import org.ititandev.model.Image;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
	static UserDAO userDAO = Application.context.getBean("UserDAO", UserDAO.class);
	static ChatDAO chatDAO = Application.context.getBean("ChatDAO", ChatDAO.class);

	// @PostMapping("/api/image")
	// String photo(@RequestParam("file") MultipartFile file,
	// @RequestParam("caption") String caption,
	// @RequestParam("location") String location) throws JSONException {
	// String username =
	// SecurityContextHolder.getContext().getAuthentication().getName();
	// Image image = new Image();
	// image.setUsername(username);
	//
	// int photo_id = photoDAO.insertPhoto(image);
	// String filename = String.valueOf(photo_id + 100) + ".jpg";
	// try {
	// byte[] bytes = file.getBytes();
	// File dir = new File(Config.getConfig("image.dir"));
	// if (!dir.exists())
	// dir.mkdirs();
	// File serverFile = new File(dir.getAbsolutePath() + File.separator +
	// filename);
	// BufferedOutputStream stream = new BufferedOutputStream(new
	// FileOutputStream(serverFile));
	// stream.write(bytes);
	// stream.close();
	// photoDAO.setPhotoFilename(filename, photo_id);
	// System.out.println("[ITitan] Upload PHOTO SUCCESS file: " +
	// serverFile.getAbsolutePath());
	// return "{\"result\":\"success\"}";
	// } catch (Exception e) {
	// e.printStackTrace();
	// return "{\"result\":\"error\"}";
	// }
	// }

	@PostMapping("/api/avatar")
	String avatar(@RequestParam("file") MultipartFile file, @RequestParam("ext") String ext, Authentication auth,
			HttpServletResponse res) throws JSONException, IOException {
		String username = auth.getName();

		int avatarId = userDAO.insertAvatar(username);
		String filename = avatarId + "." + ext;
		String avatarUrl = Config.getConfig("hostname") + "/api/avatar/" + filename;
		try {
			byte[] bytes = file.getBytes();
			File dir = new File(Config.getConfig("avatar.dir"));

			File serverFile = new File(dir.getAbsolutePath() + File.separator + filename);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();

			if (userDAO.setAvatarFilename(avatarUrl, avatarId) > 0) {
				System.out.println("[ITitan] Upload AVATAR SUCCESS file: " + serverFile.getAbsolutePath());
				return "{\"avatarUrl\": \"" + avatarUrl + "\"}";
			} else {
				res.sendError(520, "Some error has occurred");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			res.sendError(520, "Some error has occurred");
			return null;
		}
	}

	@PostMapping("/api/image")
	String image(@RequestParam("file") MultipartFile file, @RequestParam("ext") String ext, Authentication auth,
			HttpServletResponse res) throws JSONException, IOException {
		String username = auth.getName();

		int imageId = chatDAO.insertImage(username);
		String filename = imageId + "." + ext;
		String imageUrl = Config.getConfig("hostname") + "/api/image/" + filename;
		try {
			byte[] bytes = file.getBytes();
			File dir = new File(Config.getConfig("image.dir"));

			File serverFile = new File(dir.getAbsolutePath() + File.separator + filename);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();

			if (chatDAO.setImageFilename(filename, imageId) > 0) {
				System.out.println("[ITitan] Upload IMAGE SUCCESS file: " + serverFile.getAbsolutePath());
				return "{\"imageUrl\": \"" + imageUrl + "\"}";
			} else {
				res.sendError(520, "Some error has occurred");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			res.sendError(520, "Some error has occurred");
			return null;
		}
	}

	@PostMapping("/api/file")
	String file(@RequestParam("file") MultipartFile file, @RequestParam("ext") String ext, Authentication auth,
			HttpServletResponse res) throws JSONException, IOException {
		String username = auth.getName();

		int imageId = chatDAO.insertFile(username);
		String filename = imageId + "." + ext;
		String fileUrl = Config.getConfig("hostname") + "/api/file/" + filename;
		try {
			byte[] bytes = file.getBytes();
			File dir = new File(Config.getConfig("file.dir"));

			File serverFile = new File(dir.getAbsolutePath() + File.separator + filename);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();

			if (chatDAO.setFileFilename(filename, imageId) > 0) {
				System.out.println("[ITitan] Upload FILE SUCCESS file: " + serverFile.getAbsolutePath());
				return "{\"fileUrl\": \"" + fileUrl + "\"}";
			} else {
				res.sendError(520, "Some error has occurred");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			res.sendError(520, "Some error has occurred");
			return null;
		}
	}
}
