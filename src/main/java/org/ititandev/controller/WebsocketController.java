package org.ititandev.controller;

import java.util.List;

import org.ititandev.Application;
import org.ititandev.dao.ChatDAO;
import org.ititandev.model.Message;
import org.ititandev.model.MessageType;
import org.ititandev.security.TokenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class WebsocketController {
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	static ChatDAO chatDAO = Application.context.getBean("ChatDAO", ChatDAO.class);
	TokenHandler tokenHandler = new TokenHandler();

	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	@MessageMapping("/sendMessage")
	public void sendMessage(@Payload Message mes, SimpMessageHeaderAccessor headerAccessor) {
		String username;
		try {
			username = tokenHandler.parse(mes.getToken());
			if (tokenHandler.isExpired(mes.getToken())) {
				messagingTemplate.convertAndSend("/topic/" + username, "{\"error\": \"Unauthorized\"}");
				return;
			}
		} catch (Exception e) {
			username = mes.getUsername();
		}
		mes.setUsername(username);
		mes.setToken("");
		Message revMessage = mes;
		List<String> revUser;

		switch (mes.getType()) {
		case MessageType.JOIN:
			headerAccessor.getSessionAttributes().put("username", username);

			revUser = chatDAO.getRevUserJoin(username, 1);
			revUser.forEach(u -> messagingTemplate.convertAndSend("/topic/" + u, mes));
			break;

		case MessageType.TEXT:
		case MessageType.IMAGE:
		case MessageType.FILE:
		case MessageType.CALL:
			revMessage = chatDAO.insertMessage(mes);
			Message temp = revMessage;
			revUser = chatDAO.getRevUser(username, revMessage.getConversationId());
			if (revUser.size() == 0) {
				messagingTemplate.convertAndSend("/topic/" + username, "{\"error\": \"Error conversation\"}");
			}
			revUser.forEach(u -> messagingTemplate.convertAndSend("/topic/" + u, temp));
			break;

		case MessageType.READ:
			revUser = chatDAO.getRevUser(username, revMessage.getConversationId());
			chatDAO.markAsRead(mes.getConversationId(), username, mes.getContent());
			
			revUser.forEach(u -> messagingTemplate.convertAndSend("/topic/" + u, mes));
			break;
		case MessageType.TYPING:
			revUser = chatDAO.getRevUser(username, revMessage.getConversationId());
			revUser.forEach(u -> messagingTemplate.convertAndSend("/topic/" + u, mes));
			break;

		default:
			messagingTemplate.convertAndSend("/topic/" + username, "{\"error\": \"Unknown type\"}");
			break;
		}

		logger.info("[WS] " + revMessage.getType() + ": " + username + ": " + revMessage.getContent());
		System.out.println(revMessage.toString());

	}

}