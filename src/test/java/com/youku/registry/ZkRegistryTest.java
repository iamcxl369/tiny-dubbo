package com.youku.registry;

import org.junit.Test;

import com.youku.rpc.net.URL;
import com.youku.rpc.registry.ZookeeperRegistry;

public class ZkRegistryTest {

	final String registryAddress = "127.0.0.1:2181";

	@Test
	public void testRegister() {

		ZookeeperRegistry registry = new ZookeeperRegistry(registryAddress);

		registry.register(new URL("10.10.10.10", 1111));

		registry.register(new URL("10.10.10.11", 1111));

		System.out.println(registry.getServers());

		ZookeeperRegistry registry2 = new ZookeeperRegistry(registryAddress);

		registry2.subscribe();

		System.out.println(registry2.getServers());
	}

}
