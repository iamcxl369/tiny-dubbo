package com.youku.rpc.cluster.impl;

import java.util.List;

import org.springframework.util.Assert;

import com.youku.rpc.cluster.Directory;
import com.youku.rpc.invoker.Invoker;

public abstract class AbstractClusterInvoker implements Invoker {

	protected Directory directory;
	
	public AbstractClusterInvoker(Directory directory) {
		super();
		this.directory = directory;
	}

	@Override
	public Class<?> getInterfaceClass() {
		List<Invoker> invokers = directory.getInvokers();
		Assert.isTrue(!invokers.isEmpty(), "没有可用的provider");
		return invokers.get(0).getInterfaceClass();
	}

	@Override
	public ClassLoader getInterfaceClassLoader() {
		return getInterfaceClass().getClassLoader();
	}

	public Invoker select(List<Invoker> invokers) {
		return directory.getLoadBalance().select(invokers);
	}
}
