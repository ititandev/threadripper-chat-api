package org.ititandev.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ititandev.Application;
import org.ititandev.config.Config;
import org.ititandev.dao.UserDAO;
import org.ititandev.dao.ImageDAO;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@RestController
@RequestMapping("/api")
public class DownloadController {
	static ImageDAO photoDAO = Application.context.getBean("ImageDAO", ImageDAO.class);
	static UserDAO accountDAO = Application.context.getBean("UserDAO", UserDAO.class);

	@GetMapping("/avatar/{filename}.{ext}")
	FileSystemResource getPhoto(HttpServletResponse response, @PathVariable("filename") String filename,
			@PathVariable("ext") String ext) throws IOException {
		filename += "." + ext;
		if (ext.equals("png"))
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
		else if (ext.equals("gif"))
			response.setContentType(MediaType.IMAGE_GIF_VALUE);
		else
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		return new FileSystemResource(Config.getConfig("avatar.dir") + File.separator + filename);
	}

	@GetMapping("/image/{filename}.{ext}")
	FileSystemResource getAvatar(HttpServletResponse response, @PathVariable("filename") String filename,
			@PathVariable("ext") String ext) throws IOException {
		filename += "." + ext;
		if (ext.equals("png"))
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
		else if (ext.equals("gif"))
			response.setContentType(MediaType.IMAGE_GIF_VALUE);
		else
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		return new FileSystemResource(Config.getConfig("image.dir") + File.separator + filename);
	}


	@GetMapping("/file/**")
	FileSystemResource getStory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ResourceUrlProvider urlProvider = (ResourceUrlProvider) request
				.getAttribute(ResourceUrlProvider.class.getCanonicalName());
		String filename = urlProvider.getPathMatcher().extractPathWithinPattern(
				String.valueOf(request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE)),
				String.valueOf(request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)));
		System.out.print("/file/{filename} with \"" + filename + "\"");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
		return new FileSystemResource(Config.getConfig("file.dir") + File.separator + filename);
	}
}