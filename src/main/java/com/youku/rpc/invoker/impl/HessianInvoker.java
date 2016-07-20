package com.youku.rpc.invoker.impl;

import com.youku.rpc.common.ReflectUtils;
import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;
import com.youku.rpc.remote.URL;

public class HessianInvoker extends AbstractInvoker {

	public HessianInvoker(URL url, Object targetEntity, Class<?> interfaceClass) {
		super(url, targetEntity, interfaceClass);
	}

	@Override
	public Response invoke(Request request) throws RpcException {
		Object value = ReflectUtils.invokeMethod(request.getMethodName(), targetEntity, request.getArgumentTypes(),
				request.getArguments());

		Response response = new Response(request.getId());

		response.setValue(value);

		return response;

	}

}
