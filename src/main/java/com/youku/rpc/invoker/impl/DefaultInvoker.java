package com.youku.rpc.invoker.impl;

import com.youku.rpc.client.Client;
import com.youku.rpc.client.Request;
import com.youku.rpc.exception.RpcException;
import com.youku.rpc.net.URL;
import com.youku.rpc.server.Response;

public class DefaultInvoker extends AbstractInvoker {

	public DefaultInvoker(URL url, Client client, Class<?> interfaceClass) {
		super(url, client, interfaceClass);
	}

	@Override
	public Response invoke(Request request) throws RpcException {
		return client.send(request);
	}

}
