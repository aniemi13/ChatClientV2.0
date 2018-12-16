package com.niemiec.connection;

import java.io.IOException;
import java.net.Socket;

import com.niemiec.controllers.GetNickController;
import com.niemiec.objects.Client;

public class Connection extends Thread {
	private GetNickController getNickController;
	private Client client;
	private Socket socket;
	private boolean isConnected;
	private InputOutputStream inputOutputStream;
	
	public Connection(GetNickController getNickController, String host, int port) {
		this.getNickController = getNickController;
		this.client = null;
		this.isConnected = false;
		makeTheConnection(host, port);
		this.inputOutputStream = new InputOutputStream(socket);
	}

	@Override
	public void run() {
		Object object = null;
		while (true) {
			object = inputOutputStream.receiveTheObject();
			receiveTheObject(object);	
		}
	}

	private void receiveTheObject(Object object) {
		checkIfClientIsExist();
		if (client != null) {
			client.receiveTheObject(object);
		} else {
			getNickController.receiveTheObject(object);
		}
	}

	private void checkIfClientIsExist() {
		if (client == null) {
			try {
				sleep(500);
			} catch (InterruptedException e) {
			}
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
	
	public void setClient(Client client) {
		this.client = client;
	}
}
