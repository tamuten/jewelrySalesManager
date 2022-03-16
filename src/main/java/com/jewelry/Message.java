package com.jewelry;

public enum Message {
	SIGNUP("signup"), //
	UPDATE("update"), //
	DELETE("delete");//

	private Message(String key) {
		this.key = key;
	}

	private String key;

	public String getKey() {
		return this.key;
	}
}
