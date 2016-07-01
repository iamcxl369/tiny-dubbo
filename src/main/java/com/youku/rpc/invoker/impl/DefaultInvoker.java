package com.youku.rpc.invoker.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.client.Client;
import com.youku.rpc.remote.client.Request;
import com.youku.rpc.remote.server.Response;

public class DefaultInvoker extends AbstractInvoker {

	private static final Logger log = LoggerFactory.getLogger(DefaultInvoker.class);

	public DefaultInvoker(URL url, Client client, Class<?> interfaceClass) {
		super(url, client, interfaceClass);
	}

	@Override
	public Response invoke(Request request) throws RpcException {
		log.info("向服务端{}发起请求", url.toString());
		return client.send(request);
	}

}
