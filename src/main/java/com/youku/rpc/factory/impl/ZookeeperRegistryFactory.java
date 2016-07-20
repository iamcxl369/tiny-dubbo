package com.youku.rpc.factory.impl;

import com.youku.rpc.factory.RegistryFactory;
import com.youku.rpc.registry.Registry;
import com.youku.rpc.registry.impl.ZookeeperRegistry;
import com.youku.rpc.remote.support.URL;

public class ZookeeperRegistryFactory implements RegistryFactory {

	@Override
	public Registry getRegistry(URL url) {
		return new ZookeeperRegistry(url.getRegistryAddress());
	}

}
