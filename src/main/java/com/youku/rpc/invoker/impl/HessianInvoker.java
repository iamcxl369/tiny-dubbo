package com.youku.rpc.invoker.impl;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;
import com.youku.rpc.common.ReflectUtils;
import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;
import com.youku.rpc.remote.URL;

public class HessianInvoker extends AbstractInvoker {

	public HessianInvoker(URL url, Object targetEntity, Class<?> interfaceClass) {
		super(url, null, targetEntity, interfaceClass);
	}

	@Override
	public Response invoke(Request request) throws RpcException {
		HessianProxyFactory factory = new HessianProxyFactory();
		try {
			Object proxy = factory.create(interfaceClass, new StringBuilder().append("http://").append(url.getIp())
					.append(':').append(url.getPort()).toString());

			Object value = ReflectUtils.invokeMethod(request.getMethodName(), proxy, request.getArgumentTypes(),
					request.getArguments());

			Response response = new Response();

			response.setValue(value);

			return response;

		} catch (MalformedURLException e) {
			throw new RpcException(e);
		}
	}

}
