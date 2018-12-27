package com.niemiec.objects;

import com.niemiec.connection.Connection;
import com.niemiec.controllers.ChatController;
import com.niemiec.controllers.GetNickController;
import com.niemiec.logic.MessagesManagement;

public class Client {
	private Connection connection;
	private MessagesManagement messagesManagement;
	
	public Client(String host, int port) {
		messagesManagement = new MessagesManagement();
		connection = new Connection(messagesManagement, host, port);
		connection.start();
	}

	public void setUserNickToPrivateMessage(String actualInterlocutor) {
		messagesManagement.setActualInterlocutor(actualInterlocutor);
	}

	public void sendToGeneralChat(String message) {
		connection.sendTheObject(messagesManagement.sendToGeneralChat(message));
	}

	public void sendToPrivateChat(String message) {
		connection.sendTheObject(messagesManagement.sendToPrivateChat(message));
	}

	public void exit() {
		connection.sendTheObject(messagesManagement.exit());
		connection.interrupt();
	}
	
	public void setNick(String nick) {
		messagesManagement.setNick(nick);
	}

	public void readyToWork() {
		connection.sendTheObject(messagesManagement.sendReadyToWork());
	}
	
	public void setGetNickController(GetNickController getNickController) {
		messagesManagement.setGetNickController(getNickController);
	}
	
	public void setChatController(ChatController chatController) {
		messagesManagement.setChatController(chatController);
	}

	public void sendNickToCheck(String nick) {
		connection.sendTheObject(messagesManagement.sendNickToCheck(nick));
	}
}
