package com.niemiec.controllers;

import java.util.ArrayList;

import com.niemiec.objects.Client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ChatController {
	@FXML
	private HBox mainHBox;

	@FXML
	private ListView<String> generalChat;

	@FXML
	private TextField textAreaGeneralChat;

	@FXML
	private Button sendToGeneralChatButton;

	@FXML
	private ListView<String> listOfUsersChat;

	@FXML
	private ListView<String> privateChat;

	@FXML
	private TextField textAreaPrivateChat;

	@FXML
	private Button sendToPrivateChatButton;

	private Client client;
	ObservableList<String> usersList;
	ObservableList<String> privateList;

	@FXML
	void initialize() {
		usersList = FXCollections.observableArrayList();
		privateList = FXCollections.observableArrayList();
	}
	
	public void setNick(String nick) {
		this.client.setNick(nick);
	}

	@FXML
	void getUserNick(MouseEvent event) {
		ObservableList<String> userNickFromListView;
		userNickFromListView = listOfUsersChat.getSelectionModel().getSelectedItems();
		if (userNickFromListView.get(0) != null)
			client.setUserNickToPrivateMessage(userNickFromListView.get(0));
	}

	@FXML
	void sendToGeneralChat() {
		client.sendToGeneralChat(textAreaGeneralChat.getText());
		textAreaGeneralChat.clear();
	}

	@FXML
	void sendToGeneralChatAfterKeyPress(KeyEvent event) {
		if ("ENTER".equals(event.getCode().toString())) {
			sendToGeneralChat();
		}
	}

	@FXML
	void sendToPrivateChatAfterKeyPress(KeyEvent event) {
		if ("ENTER".equals(event.getCode().toString())) {
			sendToPrivateChat();
		}
	}

	@FXML
	void sendToPrivateChat() {
		client.sendToPrivateChat(textAreaPrivateChat.getText());
		textAreaPrivateChat.clear();
	}

	public ArrayList<String> getUsersList(ObservableList<String> obs) {
		ArrayList<String> a = new ArrayList<>();
		for (int i = 0; i < obs.size(); i++) {
			a.add(obs.get(i));
		}

		return a;
	}

	public void updateUsersList(ArrayList<String> list) {
		Platform.runLater(() -> {
			listOfUsersChat.getItems().clear();
			this.usersList.addAll(list);
			listOfUsersChat.setItems(this.usersList);

		});
	}

	public void addMessageToGeneralChat(String message) {
		Platform.runLater(() -> {
			generalChat.getItems().add(message);
		});
	}

	public ObservableList<String> getUsersList() {
		return usersList;
	}

	public ListView<String> getListOfUsersChat() {
		return listOfUsersChat;
	}

	public void addMessageToPrivateChat(String message) {
		Platform.runLater(() -> {
			privateChat.getItems().add(message);
		});
	}

	public void updatePrivateChat(ArrayList<String> list) {
		if (!list.isEmpty()) {
			privateChat.getItems().clear();
			this.privateList.addAll(list);
			privateChat.setItems(this.privateList);
		}
	}

	public void lockPrivateChat() {
		privateChat.getItems().clear();
		sendToPrivateChatButton.setDisable(true);
	}

	public void unlockPrivateChat() {
		sendToPrivateChatButton.setDisable(false);
	}

	public void clearPrivateListView() {
		privateChat.getItems().clear();
	}
	
	@FXML
	public void exit() {
		client.exit();
		System.exit(-1);
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
