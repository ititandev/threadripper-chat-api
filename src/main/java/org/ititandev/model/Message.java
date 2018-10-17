package org.ititandev.model;

import java.util.Date;

public class Message {

	String type;
	String content;
	Date datetime;
	String conversationId;
	String fromUsername;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}
	
	public static class MessageType {
		public static final String JOIN = "JOIN"; 
		public static final String LEAVE = "LEAVE";
		public static final String TEXT = "TEXT";
		public static final String IMAGE = "IMAGE";
		public static final String FILE = "FILE";
		public static final String CALL = "CALL";
	}
}



