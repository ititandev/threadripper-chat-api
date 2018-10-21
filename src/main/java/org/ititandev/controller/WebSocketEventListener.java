package org.ititandev.controller;

import java.util.List;

import org.ititandev.Application;
import org.ititandev.dao.ChatDAO;
import org.ititandev.model.Message;
import org.ititandev.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	static ChatDAO chatDAO = Application.context.getBean("ChatDAO", ChatDAO.class);

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		logger.info("[WS] Received a new web socket connection");
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String username = (String) headerAccessor.getSessionAttributes().get("username");
		if (username != null) {
			logger.info("[WS] User Disconnected : " + username);

			Message message = new Message();
			message.setType(MessageType.LEAVE);
			message.setUsername(username);

			List<String> revUser = chatDAO.getRevUserJoin(username, 0);
			revUser.forEach(u -> messagingTemplate.convertAndSend("/topic/" + u, message));
		}
	}
}