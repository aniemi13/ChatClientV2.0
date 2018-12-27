package com.niemiec.controllers;

import java.io.IOException;
import java.util.regex.Pattern;

import com.niemiec.objects.Client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GetNickController {
	@FXML
	private Stage stage;

	@FXML
	private VBox mainVBox;

	@FXML
	private TextField textAreaNick;

	@FXML
	private Label informationLabel;

	@FXML
	private Button saveNickButton;
	
	@FXML
	private FXMLLoader loader = null;
	
	private String nick = null;
	private Client client = null;
	private boolean nickIsOk = false;

	@FXML
	void initialize() {
		client = new Client("localhost", 6666);
		client.setGetNickController(this);
	}

	@FXML
	void saveNick(ActionEvent event) {
		checkNick();
	}

	private FXMLLoader getFXMLLoader() {
		return new FXMLLoader(getClass().getResource("/fxml/ChatWindow.fxml"));
	}

	private void checkNick() {
		getNick();
		if (!Pattern.matches("[a-zA-Z]{1}[a-zA-Z0-9]{2,14}", nick)) {
			informationLabel.setText("Błędny nick");
			return;
		}
		client.sendNickToCheck(nick);
	}

	private void getNick() {
		nick = textAreaNick.getText();
		if (nick == null) {
			nick = "";
		}
	}

	public void initData(Stage stage) {
		this.stage = stage;
	}

	public boolean getNickIsOk() {
		return nickIsOk;
	}

	private void viewChatAndSendNick() {
		Platform.runLater(() -> {

			try {
				loader = getFXMLLoader();
				HBox chatWindow = loader.load();
				
				ChatController cc = loader.getController();
				updateClientData(cc);
				cc.setClient(client);
				
				mainVBox.getChildren().setAll(chatWindow);
				stage.close();
				stage.setWidth(chatWindow.getPrefWidth());
				stage.setHeight(chatWindow.getPrefHeight() + 20);
				stage.centerOnScreen();
				stage.show();
			} catch (IOException e) {
				System.out.println("Nie udało się wczytać nowego okna: " + e);
			}
		});
	}

	private void updateClientData(ChatController cc) {
		client.setNick(nick);
		client.setChatController(cc);
		client.readyToWork();
	}

	public void reciveTheInformation(boolean nickIsOk) {
		Platform.runLater(() -> {
			if (!nickIsOk) {
				informationLabel.setText("Wybrany przez Ciebie link jest już zajęty");
				return;
			}
			this.nickIsOk = true;
			viewChatAndSendNick();

		});
	}
}
