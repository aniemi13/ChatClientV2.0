package com.niemiec.logic;

public class CheckNickManagement {
	private final String CHECK_NICK = "cn";
	private final String NICK_NOTEXIST = "notexist";
	private final String SEPARATOR = "/";
	
	public Object sendNick(String nick) {
		return new String(SEPARATOR + CHECK_NICK + SEPARATOR + nick);
	}


	public boolean checkIfNickIsOk(String answer) {
		String[] a;
		a = answer.split(SEPARATOR, 3);
		if (a[1].equals(CHECK_NICK) && a[2].equals(NICK_NOTEXIST)) {
			return true;
		}
		return false;
	}
}
