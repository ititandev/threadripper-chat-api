package org.ititandev.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.ititandev.Application;
import org.ititandev.config.Config;
import org.ititandev.dao.UserDAO;
import org.ititandev.dao.ImageDAO;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DownloadController {
	static ImageDAO photoDAO = Application.context.getBean("ImageDAO", ImageDAO.class);
	static UserDAO accountDAO = Application.context.getBean("UserDAO", UserDAO.class);

	@GetMapping("/api/photo/{filename}.{ext}")
	byte[] getPhoto(HttpServletResponse response, @PathVariable("filename") String filename,
			@PathVariable("ext") String ext) throws IOException {
		filename += "." + ext;
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".jpg\"");
		InputStream inputStream = new FileInputStream(Config.getConfig("photo.dir") + File.separator + filename);
		BufferedImage img = ImageIO.read(inputStream);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", byteStream);
		return byteStream.toByteArray();

	}

	@GetMapping("/api/avatar/{filename}.{ext}")
	byte[] getAvatar(HttpServletResponse response, @PathVariable("filename") String filename,
			@PathVariable("ext") String ext) throws IOException {
		filename += "." + ext;
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		InputStream inputStream = new FileInputStream(Config.getConfig("avatar.dir") + File.separator + filename);
		BufferedImage img = ImageIO.read(inputStream);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", byteStream);
		return byteStream.toByteArray();
	}

	@GetMapping("/api/file/{filename}.{ext}")
	byte[] getStory(HttpServletResponse response, @PathVariable("story_id") String filename,
			@PathVariable("ext") String ext) throws IOException {
		filename += "." + ext;
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".jpg\"");
		InputStream inputStream = new FileInputStream(Config.getConfig("story.dir") + File.separator + filename);
		BufferedImage img = ImageIO.read(inputStream);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ImageIO.write(img, "jpg", byteStream);
		return byteStream.toByteArray();
	}
	// @RestController
	// @RequestMapping("/image")
	// class DownloadController {
	//
	// @RequestMapping(value = "/download", method = RequestMethod.GET)
	// void downloadImage(HttpServletResponse response){
	//
	// ClassPathResource imageFile = new ClassPathResource("holmes.jpg")
	// response.setContentType(MediaType.IMAGE_JPEG_VALUE)
	// response.setHeader("Content-Disposition", "attachment;
	// filename=\"sherlock_holmes.jpg\"")
	// StreamUtils.copy(imageFile.getInputStream(), response.getOutputStream())
	// }
	//
	// @RequestMapping(value = "/show", method = RequestMethod.GET)
	// void showImage(HttpServletResponse response){
	//
	// ClassPathResource imageFile = new ClassPathResource("holmes.jpg")
	// response.setContentType(MediaType.IMAGE_JPEG_VALUE)
	// response.setHeader("Content-Disposition", "inline;
	// filename=\"sherlock_holmes.jpg\"")
	// StreamUtils.copy(imageFile.getInputStream(), response.getOutputStream())
	// }
	// }
}