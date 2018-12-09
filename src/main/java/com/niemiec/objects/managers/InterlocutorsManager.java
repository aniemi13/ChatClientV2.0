package com.niemiec.objects.managers;

import java.util.ArrayList;
import java.util.List;

import com.niemiec.objects.Interlocutor;

public class InterlocutorsManager {
	private List<Interlocutor> interlocutors;
	
	public InterlocutorsManager() {
		this.interlocutors = new ArrayList<Interlocutor>();
	}
	
	public boolean isExist(String nick) {
		return (getInterlocutor(nick) != null);
	}
	
	public void addInterlocutor(String nick) {
		Interlocutor interlocutor = new Interlocutor(nick);
		interlocutors.add(interlocutor);
	}
	
	public boolean messageIsRead(String nick) {
		return getInterlocutor(nick).messageIsRead();
	}
	
	public void setMessageIsRead(String nick, boolean messageIsRead) {
		getInterlocutor(nick).setMessageIsRead(messageIsRead);
	}
	
	public boolean isOnline(String nick) {
		return getInterlocutor(nick).isOnline();
	}
	
	public void setOnline(String nick, boolean isOnline) {
		getInterlocutor(nick).setOnline(isOnline);
	}
	
	public void addMessage(String nick, String message) {
		getInterlocutor(nick).addMessage(message);
	}

	public Interlocutor getInterlocutor(String nick) {
		for (Interlocutor i : interlocutors) {
			if (i.getNick().equals(nick)) {
				return i;
			}
		}
		return null;
	}
	
	public ArrayList<String> getMessages(String nick) {
		return getInterlocutor(nick).getMessages();
	}
	
	public boolean haveMessages(String nick) {
		if (!getInterlocutor(nick).getMessages().isEmpty())
			return true;
		return false;
	}
}
