package com.youku.registry;

import org.junit.Test;

import com.youku.rpc.config.RegistryConfig;

public class TestRegistryConfig {

	@Test
	public void testConfigCreator() {
		RegistryConfig config = new RegistryConfig("zk://ip:port");

		System.out.println(config.getAddress());

		System.out.println(config.getProtocol());

	}
}
