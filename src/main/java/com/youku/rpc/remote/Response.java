package com.youku.rpc.remote;

public class Response {

	private Object value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Response [value=" + value + "]";
	}
}
