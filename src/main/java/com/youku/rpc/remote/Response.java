package com.youku.rpc.remote;

public class Response extends BaseMessage {

	public Response(String id) {
		super(id);
	}

	private Object value;

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Response [id=" + id + ", value=" + value + "]";
	}

	@Override
	public byte getType() {
		return MessageType.RESPONSE.type();
	}

}
