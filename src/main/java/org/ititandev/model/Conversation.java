package org.ititandev.model;

import java.util.List;

public class Conversation {

	String conversationName;
	List<UserConversation> listUser;
	Message lastMessage;
	String conversationId;
	Boolean online;
	int notiCount;

	public List<UserConversation> getListUser() {
		return listUser;
	}

	public void setListUser(List<UserConversation> listUser) {
		this.listUser = listUser;
	}

	public String getConversationName() {
		return conversationName;
	}

	public void setConversationName(String conversationName) {
		this.conversationName = conversationName;
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
