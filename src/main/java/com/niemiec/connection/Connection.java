package com.niemiec.connection;

import java.io.IOException;
import java.net.Socket;

import com.niemiec.logic.MessagesManagement;

public class Connection extends Thread {
	private MessagesManagement messagesManagement;
	private Socket socket;
	private boolean isConnected;
	private InputOutputStream inputOutputStream;

	public Connection(MessagesManagement messagesManagement, String host, int port) {
		this.messagesManagement = messagesManagement;
		this.isConnected = false;
		makeTheConnection(host, port);
		this.inputOutputStream = new InputOutputStream(socket);
	}

	@Override
	public void run() {
		Object object = null;
		while (true) {
			object = inputOutputStream.receiveTheObject();
			messagesManagement.receiveTheObject(object);
		}
	}

	public void makeTheConnection(String host, int port) {
		socket = null;
		try {
			socket = new Socket(host, port);
			isConnected = true;
		} catch (Exception e) {
			System.out.println("Błąd tworzenia połączenia: " + e);
		}
	}

	@SuppressWarnings("deprecation")
	public void interrupt() {
		this.stop();
		inputOutputStream.closeInputOutputStream();
		super.interrupt();
		try {
			socket.close();
			isConnected = false;
		} catch (IOException e) {
		}
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void sendTheObject(Object object) {
		inputOutputStream.sendTheObject(object);
	}
}
