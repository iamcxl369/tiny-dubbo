package com.youku.rpc.remote.server;

import java.io.Serializable;

public class Response implements Serializable {

	private Object value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
