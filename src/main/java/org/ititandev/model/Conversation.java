package org.ititandev.model;

import java.util.List;

public class Conversation {

	String conversationName;
	List<String> displayName;
	List<String> username;
	List<String> avatarUrl;
	Message lastMessage;
	String conversationId;
	Boolean online;
	int notiCount;

	public String getConversationName() {
		return conversationName;
	}

	public void setConversationName(String conversationName) {
		this.conversationName = conversationName;
	}

	public List<String> getDisplayName() {
		return displayName;
	}

	public void setDisplayName(List<String> displayName) {
		this.displayName = displayName;
	}

	public List<String> getUsername() {
		return username;
	}

	public void setUsername(List<String> username) {
		this.username = username;
	}

	public List<String> getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(List<String> avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Message getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(Message lastMessage) {
		this.lastMessage = lastMessage;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public int getNotiCount() {
		return notiCount;
	}

	public void setNotiCount(int notiCount) {
		this.notiCount = notiCount;
	}

}
