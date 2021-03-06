package com.youku.rpc.invoker.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.common.Const;
import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.client.Client;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.Response;
import com.youku.rpc.remote.support.ResponseFuture;
import com.youku.rpc.remote.support.URL;

public class NevermoreInvoker extends AbstractInvoker {

	private static final Logger log = LoggerFactory.getLogger(NevermoreInvoker.class);

	private Client client;

	public NevermoreInvoker(URL url, Client client, Object targetEntity, Class<?> interfaceClass) {
		super(url, targetEntity, interfaceClass);
		this.client = client;
	}

	@Override
	public Response invoke(final Request request) throws RpcException {
		log.debug("向服务端{}发起请求", url.toString());

		boolean async = url.getBooleanParam(Const.ASYNC_KEY, Const.DEFAULT_ASYNC);

		if (async) {
			log.debug("异步请求");
			ResponseFuture future = client.request(request);
			
			throw new UnsupportedOperationException("暂不支持异步处理");
		} else {
			log.debug("同步请求");
			return client.request(request).get(url.getIntParam(Const.TIMEOUT_KEY, Const.DEFAULT_TIMEOUT));
		}
	}

}
