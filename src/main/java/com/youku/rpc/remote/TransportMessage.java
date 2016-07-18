package com.youku.rpc.remote;

public class TransportMessage {

	private Header header;

	private Body body;

	public Header getHeader() {
		return header;
	}

	public Body getBody() {
		return body;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "TransportMessage [header=" + header + ", body=" + body + "]";
	}

}
