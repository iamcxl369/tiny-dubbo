package com.youku.rpc.remote.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.youku.rpc.common.UUIDUtil;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;

public class RemoteInvocationHandler implements InvocationHandler {

	private Invoker invoker;

	public RemoteInvocationHandler(Invoker invoker) {
		this.invoker = invoker;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Request request = new Request(UUIDUtil.uuid());
		request.setArguments(args);
		request.setMethodName(method.getName());
		request.setArgumentTypes(method.getParameterTypes());
		request.setInterfaceName(method.getDeclaringClass().getName());

		Response response = invoker.invoke(request);

		return response.getValue();
	}
}
