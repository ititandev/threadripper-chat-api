package org.ititandev.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.ititandev.Application;
import org.ititandev.dao.ChatDAO;
import org.ititandev.mapper.ConversationMapper;
import org.ititandev.model.Conversation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
	static ChatDAO chatDAO = Application.context.getBean("ChatDAO", ChatDAO.class);

	public static List<String> getStringListFromJsonArray(JSONArray jArray) throws JSONException {
		List<String> returnList = new ArrayList<String>();
		for (int i = 0; i < jArray.length(); i++) {
			String val = jArray.getString(i);
			returnList.add(val);
		}
		return returnList;
	}
	
	@GetMapping("/api/conversation")
	public List<Conversation> getConversation(Authentication authentication) {
		String username = authentication.getName();
		
		
		return chatDAO.getConversation(username);
	}
	
	@PostMapping(value = "/api/conversation")
	public Object addConversation(@RequestBody String body, HttpServletResponse res) throws JSONException, IOException {
		JSONArray json = new JSONObject(body).getJSONArray("listUsername");
		List<String> listUsername = getStringListFromJsonArray(json);
		
		String conversationId = chatDAO.addConversation(listUsername);
		if (conversationId.equals("0")) {
			res.sendError(520, "Some error has occurred");
			return null;
		}
		return "{\"" + "conversationId" + "\": \"" + conversationId + "\"}";
	}
}
