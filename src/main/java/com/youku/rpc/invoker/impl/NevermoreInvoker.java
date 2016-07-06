package com.youku.rpc.invoker.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.client.Client;

public class NevermoreInvoker extends AbstractInvoker {

	private static final Logger log = LoggerFactory.getLogger(NevermoreInvoker.class);

	public NevermoreInvoker(URL url, Client client, Object targetEntity, Class<?> interfaceClass) {
		super(url, client, targetEntity, interfaceClass);
	}

	@Override
	public Response invoke(Request request) throws RpcException {
		log.info("向服务端{}发起请求", url.toString());
		return client.send(request);
	}

}
