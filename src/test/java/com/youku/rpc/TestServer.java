package com.youku.rpc;

import java.util.Random;

import com.youku.rpc.config.ProtocolConfig;
import com.youku.rpc.config.RegistryConfig;
import com.youku.rpc.config.ServiceConfig;
import com.youku.rpc.service.UserService;
import com.youku.rpc.service.impl.UserServiceImpl;

public class TestServer {

	private static final Random random = new Random();

	public static void main(String[] args) {

		int[] weights = { 100, 200, 100, 300 };

		int weight = random(weights);

		UserService userService = new UserServiceImpl();

		ProtocolConfig protocolConfig = new ProtocolConfig(TestConsts.SERVER_PORT);

		RegistryConfig registryConfig = new RegistryConfig(TestConsts.ZK_REGISTRY_ADDRESS);

		ServiceConfig<UserService> serviceConfig = new ServiceConfig<>();

		serviceConfig.setInterfaceClass(UserService.class);

		serviceConfig.setProtocolConfig(protocolConfig);

		serviceConfig.setRef(userService);

		serviceConfig.setRegistryConfig(registryConfig);

		serviceConfig.setWeight(weight);

		serviceConfig.export();

	}

	private static int random(int[] weights) {
		return weights[random.nextInt(weights.length)];
	}

}
