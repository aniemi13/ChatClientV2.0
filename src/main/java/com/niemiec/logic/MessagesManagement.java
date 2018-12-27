package com.niemiec.logic;

import java.util.ArrayList;

import com.niemiec.controllers.ChatController;
import com.niemiec.controllers.GetNickController;
import com.niemiec.objects.GeneralChat;
import com.niemiec.objects.managers.InterlocutorsManager;

public class MessagesManagement {
	private final String PRIVATE_MESSAGE = "pm";
	private final String GROUP_MESSAGE = "gm";
	private final String CHECK_NICK_MESSAGE = "cn";
	private final String NICK_NOTEXIST = "notexist";
	private final String SEPARATOR = "/";
	private final String READY_TO_WORK = "rw";

	private ChatController chatController;
	private GetNickController getNickController;
	private InterlocutorsManager interlocutorsManager;
	private GeneralChat generalChat;
	private String message;
	private String typeOfMessage;
	private String nick;
	private String senderNick;
	private String rightMessage;
	private String actualInterlocutor;

	public MessagesManagement() {
		this.actualInterlocutor = "";
		this.interlocutorsManager = new InterlocutorsManager();
		this.generalChat = new GeneralChat();
	}

	public void receiveTheObject(Object object) {
		if (object instanceof String) {
			messageIsString(object);
		} else if (object instanceof ArrayList) {
			messageIsArrayList(object);
		}
	}

	public void messageIsString(Object object) {
		message = (String) object;
		separateMessage();
		
		if (typeOfMessage.equals(PRIVATE_MESSAGE)) {
			receivedPrivateMessage();
		} else if (typeOfMessage.equals(GROUP_MESSAGE)) {
			receivedAndViewGroupMessage();
		} else if (typeOfMessage.equals(CHECK_NICK_MESSAGE)) {
			receivedCheckNickMessage();
		}
		message = null;
	}

	private void receivedCheckNickMessage() {

		boolean nickIsOk = false;
		if (rightMessage.equals(NICK_NOTEXIST)) {
			nickIsOk = true;
		}
		getNickController.reciveTheInformation(nickIsOk);
	}

	private void receivedPrivateMessage() {
		if (!interlocutorsManager.isExist(senderNick)) {
			interlocutorsManager.addInterlocutor(senderNick);
		}
		String m = ((senderNick.equals(nick)) ? "TY" : senderNick) + "> " + rightMessage;
		interlocutorsManager.addMessage(senderNick, m);
		if (senderNick.equals(actualInterlocutor)) {
			interlocutorsManager.setMessageIsRead(senderNick, true);
			addMessageToPrivateChat();
		} else {
			// TODO jeżeli nie to podświetlenie pola
			interlocutorsManager.setMessageIsRead(senderNick, false);
		}
	}

	private void receivedAndViewGroupMessage() {
		String m = ((senderNick.equals(nick)) ? "TY" : senderNick) + "> " + rightMessage;
		generalChat.addMessage(m);
		chatController.addMessageToGeneralChat(m);
	}

	private void separateMessage() {
		getTypeOfMessageFromMessage();
		if (typeOfMessage.equals(PRIVATE_MESSAGE)) {
			getNickAndInterlocutorNickAndRightMessageFromMessage();
		} else if (typeOfMessage.equals(GROUP_MESSAGE)) {
			getNickAndRightMessageFromMessage();
		} else if (typeOfMessage.equals(CHECK_NICK_MESSAGE)) {
			getCheckNickInformation();
		}
	}

	private void getCheckNickInformation() {
		String[] s = message.split(SEPARATOR, 3);
		typeOfMessage = s[1];
		rightMessage = s[2];
	}

	private void getNickAndInterlocutorNickAndRightMessageFromMessage() {
		String[] s = message.split(SEPARATOR, 5);
		typeOfMessage = s[1];
		senderNick = s[2];
		rightMessage = s[4];
	}

	private void getNickAndRightMessageFromMessage() {
		String[] s = message.split(SEPARATOR, 4);
		typeOfMessage = s[1];
		senderNick = s[2];
		rightMessage = s[3];
	}

	private void getTypeOfMessageFromMessage() {
		typeOfMessage = message.substring(1, 3);
	}

	@SuppressWarnings("unchecked")
	public void messageIsArrayList(Object object) {
		chatController.updateUsersList((ArrayList<String>) object);
	}

	public void setActualInterlocutor(String selectedNick) {

		if (selectedNick.equals(this.nick)) {
			chatController.lockPrivateChat();
			actualInterlocutor = "";
		} else if (!selectedNick.equals(actualInterlocutor)) {
			if (!interlocutorsManager.isExist(selectedNick)) {
				interlocutorsManager.addInterlocutor(selectedNick);
			}
			actualInterlocutor = selectedNick;
			if (interlocutorsManager.haveMessages(selectedNick)) {
				updatePrivateChatListView();

				// TODO WIADOMOŚĆ ODCZYTANA - ZNIKA PODŚWIETLENIE UŻYTKOWNIKA
			} else {
				chatController.clearPrivateListView();
			}
			chatController.unlockPrivateChat();
			// aktualizacja wyświetlania prywatnej wiadomości - ustawienie, że wiadomość
			// odczytana
			// i wyświetlenie jej
		}

	}

	private void updatePrivateChatListView() {
		chatController.updatePrivateChat(interlocutorsManager.getMessages(actualInterlocutor));
	}

	private void addMessageToPrivateChat() {
		String m = ((senderNick.equals(nick)) ? "TY" : senderNick) + "> " + rightMessage;
		chatController.addMessageToPrivateChat(m);
	}

	public Object sendToGeneralChat(String message) {
		return new String(SEPARATOR + GROUP_MESSAGE + SEPARATOR + nick + SEPARATOR + message);
	}

	public void highlightTheFieldInListView(String nick) {

	}

	public Object sendToPrivateChat(String message) {
		String m = "TY> " + message;
		chatController.addMessageToPrivateChat(m);
		interlocutorsManager.addMessage(actualInterlocutor, m);
		return new String(SEPARATOR + PRIVATE_MESSAGE + SEPARATOR + nick + SEPARATOR + actualInterlocutor + SEPARATOR + message);
	}

	public Object exit() {
		return new String("/exit/" + nick);
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public Object sendReadyToWork() {
		return new String(SEPARATOR + READY_TO_WORK + SEPARATOR + nick);
	}
	
	public void setChatController(ChatController chatController) {
		this.chatController = chatController;
	}

	public void setGetNickController(GetNickController getNickController) {
		this.getNickController = getNickController;
	}

	public Object sendNickToCheck(String nick) {
		return new String(SEPARATOR + CHECK_NICK_MESSAGE + SEPARATOR + nick);
	}
}
