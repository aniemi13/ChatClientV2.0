package com.niemiec.objects;

import java.util.ArrayList;

public class Interlocutor {
	private ArrayList<String> messages;
	private String nick;
	private boolean isOnline;
	private boolean messageIsRead;
	
	public Interlocutor(String nick) {
		this.nick = nick;
		this.messages = new ArrayList<String>();
		this.isOnline = true;
		this.messageIsRead = false;
	}
	
	public void addMessage(String message) {
		messages.add(message);
	}
	
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	public boolean isOnline() {
		return isOnline;
	}
	
	public void setMessageIsRead(boolean messageIsRead) {
		this.messageIsRead = messageIsRead;
	}
	
	public boolean messageIsRead() {
		return messageIsRead;
	}
	
	public String getNick() {
		return nick;
	}
	
	public ArrayList<String> getMessages() {
		return messages;
	}
}
