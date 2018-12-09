package com.niemiec.connection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class InputOutputStream {
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;

	public InputOutputStream(Socket socket) {
		this.socket = socket;
		createOutputStream();
		createInputStream();
	}
	
	public void sendTheObject(Object object) {
		try {
			outputStream.writeObject(object);
		} catch (IOException e) {
			System.out.println("Błąd wysyłania danych: " + e);
		}
	}
	
	public Object receiveTheObject() {
		try {
			return inputStream.readObject();
		} catch (Exception e) {
			System.out.println("Błąd odbierania danych: " + e);
		}
		return null;
	}

	private void createOutputStream() {
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.flush();
		} catch (IOException e) {
			System.out.println("Błąd tworzenia strumienia wyjściowego: " + e);
		}
	}

	private void createInputStream() {
		try {
			inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Błąd tworzenia strumienia wejściowego: " + e);
		}
	}
	
	public void closeInputOutputStream() {
		try {
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
		}
	}
}
