package com.youku.rpc.remote.support;

public abstract class BaseMessage {

	protected String id;

	public BaseMessage(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public abstract byte getType();
}
