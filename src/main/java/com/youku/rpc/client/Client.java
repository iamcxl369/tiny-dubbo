package com.youku.rpc.client;

import com.youku.rpc.server.Response;

public interface Client {

	void open();

	void close();

	Response send(Request request);
}
