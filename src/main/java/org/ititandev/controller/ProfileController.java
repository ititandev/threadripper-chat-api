package org.ititandev.controller;

import org.ititandev.Application;
import org.ititandev.dao.UserDAO;
import org.ititandev.dao.ImageDAO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
	static ImageDAO photoDAO = Application.context.getBean("ImageDAO", ImageDAO.class);
	static UserDAO accountDAO = Application.context.getBean("UserDAO", UserDAO.class);

//	@GetMapping("/profile/{username}")
//	public ProfilePage getUserPage(@PathVariable("username") String username) {
//		String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
//		if (accountDAO.checkBlock(username, currentUser))
//			return null;
//		else {
//			ProfilePage userPage = photoDAO.getProfile(username, currentUser);
//			return userPage;
//		}
//	}

	
}
