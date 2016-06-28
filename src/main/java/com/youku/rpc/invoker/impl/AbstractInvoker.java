package com.youku.rpc.invoker.impl;

import com.youku.rpc.client.Client;
import com.youku.rpc.invoker.Invoker;

public abstract class AbstractInvoker implements Invoker {

	protected Client client;

	protected Class<?> interfaceClass;

	public AbstractInvoker(Client client, Class<?> interfaceClass) {
		this.client = client;
		this.interfaceClass = interfaceClass;
	}

	@Override
	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}

	@Override
	public ClassLoader getInterfaceClassLoader() {
		return interfaceClass.getClassLoader();
	}

}
