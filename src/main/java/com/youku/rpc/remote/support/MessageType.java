package com.youku.rpc.remote.support;

public enum MessageType {
	REQUEST((byte) 0, "请求"), RESPONSE((byte) 1, "响应");

	private byte type;

	private String desc;

	private MessageType(byte type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public byte type() {
		return type;
	}

	public String desc() {
		return desc;
	}

	public static MessageType findByType(int type) {
		MessageType[] messageTypes = MessageType.values();
		for (MessageType messageType : messageTypes) {
			if (messageType.type == type) {
				return messageType;
			}
		}
		return null;
	}

	public static MessageType findByDesc(String desc) {
		MessageType[] messageTypes = MessageType.values();
		for (MessageType messageType : messageTypes) {
			if (messageType.desc.equals(desc)) {
				return messageType;
			}
		}
		return null;
	}

}
