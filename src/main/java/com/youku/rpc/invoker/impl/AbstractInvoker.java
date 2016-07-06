package com.youku.rpc.invoker.impl;

import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.client.Client;

public abstract class AbstractInvoker implements Invoker {

	protected Client client;

	protected Class<?> interfaceClass;

	protected URL url;

	protected Object targetEntity;

	public AbstractInvoker(URL url, Client client, Object targetEntity, Class<?> interfaceClass) {
		this.url = url;
		this.client = client;
		this.targetEntity = targetEntity;
		this.interfaceClass = interfaceClass;
	}

	@Override
	public Object getTargetEntity() {
		return targetEntity;
	}

	@Override
	public Class<?> getInterfaceClass() {
		return interfaceClass;
	}

	@Override
	public ClassLoader getInterfaceClassLoader() {
		return interfaceClass.getClassLoader();
	}

	@Override
	public URL getURL() {
		return url;
	}

}
