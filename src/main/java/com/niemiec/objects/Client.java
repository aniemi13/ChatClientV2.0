package com.niemiec.objects;

import com.niemiec.connection.Connection;
import com.niemiec.controllers.ChatController;
import com.niemiec.controllers.GetNickController;
import com.niemiec.logic.MessagesManagement;


public class Client {
	private Connection connection;
	private MessagesManagement messagesManagement;

	public Client(ChatController chatController, String nick) {
		createConnection();
		this.messagesManagement = new MessagesManagement(chatController, nick);
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

	public void receiveTheObject(Object object) {
		messagesManagement.receiveTheObject(object);
	}

	private void createConnection() {
		this.connection = GetNickController.getConnection();
		this.connection.setClient(this);
	}

	public void exit() {
		connection.sendTheObject(messagesManagement.exit());
		connection.interrupt();
	}
}
