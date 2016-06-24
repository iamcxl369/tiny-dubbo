package com.youku.rpc.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.youku.rpc.server.Response;

public class RemoteInvocationHandler implements InvocationHandler {

	private Invoker invoker;

	public RemoteInvocationHandler(Invoker invoker) {
		this.invoker = invoker;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Request request = new Request();

		request.setArguments(args);
		request.setMethodName(method.getName());
		request.setArgumentTypes(method.getParameterTypes());
		request.setInterfaceClass(method.getDeclaringClass());

		Response response = invoker.getClient().send(request);

		return response.getValue();
	}
}
