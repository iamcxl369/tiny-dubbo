package com.youku.rpc.client;

import java.lang.reflect.Proxy;

public class ProxyFactory {

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(Invoker invoker) {
		return (T) Proxy.newProxyInstance(invoker.getInterfaceClassLoader(), new Class<?>[] { invoker.getInterfaceClass() },
				new RemoteInvocationHandler(invoker));
	}

}
