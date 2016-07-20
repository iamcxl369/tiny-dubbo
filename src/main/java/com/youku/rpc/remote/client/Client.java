package com.youku.rpc.remote.client;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.ResponseFuture;

public interface Client {

	void open();

	void close();

	void send(Request request);

	ResponseFuture request(Request request) throws RpcException;
}
