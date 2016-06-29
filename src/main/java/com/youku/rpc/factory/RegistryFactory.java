package com.youku.rpc.factory;

import com.youku.rpc.registry.RegistryService;
import com.youku.rpc.registry.impl.ZookeeperRegistry;

public class RegistryFactory {

	public static RegistryService create(String protocol,String address) {
		// TODO 目前支支持zk注册中心
		return new ZookeeperRegistry(address);
	}

}
