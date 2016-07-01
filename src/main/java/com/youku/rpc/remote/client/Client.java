package com.youku.rpc.remote.client;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;

public interface Client {

	void open();

	void close();

	Response send(Request request) throws RpcException;
}
