package com.niemiec.objects;

import java.util.ArrayList;
import java.util.List;

public class GeneralChat {
	private List<String> messages;
	
	public GeneralChat() {
		this.messages = new ArrayList<String>();
	}
	
	public void addMessage(String message) {
		messages.add(message);
	}
	
	public List<String> getMessages() {
		return messages;
	}
}
