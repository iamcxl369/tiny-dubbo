package com.youku.rpc.factory;

import java.lang.reflect.Proxy;

import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.client.RemoteInvocationHandler;

public class ProxyFactory {

	@SuppressWarnings("unchecked")
	public static <T> T createProxy(Invoker invoker) {
		return (T) Proxy.newProxyInstance(invoker.getInterfaceClassLoader(),
				new Class<?>[] { invoker.getInterfaceClass() }, new RemoteInvocationHandler(invoker));
	}

}
