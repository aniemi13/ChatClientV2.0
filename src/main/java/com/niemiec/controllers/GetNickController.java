package com.niemiec.controllers;

import java.io.IOException;
import java.util.regex.Pattern;

import com.niemiec.connection.Connection;
import com.niemiec.logic.CheckNickManagement;

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

	protected static String nick = null;
	private FXMLLoader loader = null;
	private static Connection connection;
	private CheckNickManagement checkNickManagement = null;
	private boolean nickIsOk = false;

	@FXML
	void initialize() {
		connection = new Connection(this, "localhost", 6666);
		connection.start();
		checkNickManagement = new CheckNickManagement();
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
		sendNickForCheck();
	}

	private void sendNickForCheck() {
		connection.sendTheObject(checkNickManagement.sendNick(nick));
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

	public void receiveTheObject(Object object) {
		Platform.runLater(() -> {
			String answer = (String) object;
			if (!checkNickManagement.checkIfNickIsOk(answer)) {
				informationLabel.setText("Taki nick już istnieje w bazie");
				return;
			}
			nickIsOk = true;
			viewChatAndSendNick();

		});
	}

	public boolean getNickIsOk() {
		return nickIsOk;
	}

	public static Connection getConnection() {
		return connection;
	}

	// TODO brzydko wygląda, ale póki co zostawię
	private void viewChatAndSendNick() {
		Platform.runLater(() -> {

			try {
				loader = getFXMLLoader();
				HBox chatWindow = loader.load();
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
}
