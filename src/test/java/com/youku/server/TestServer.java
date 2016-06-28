package com.youku.server;

import com.youku.rpc.config.ProtocolConfig;
import com.youku.rpc.config.RegistryConfig;
import com.youku.rpc.config.ServiceConfig;
import com.youku.rpc.service.UserService;
import com.youku.rpc.service.impl.UserServiceImpl;

public class TestServer {

	public static void main(String[] args) {
		
		UserService userService = new UserServiceImpl();

		ProtocolConfig protocolConfig = new ProtocolConfig(TestConsts.SERVER_PORT);

		RegistryConfig registryConfig = new RegistryConfig(TestConsts.ZK_REGISTRY_ADDRESS);

		ServiceConfig<UserService> serviceConfig = new ServiceConfig<>();

		serviceConfig.setInterfaceClass(UserService.class);

		serviceConfig.setProtocolConfig(protocolConfig);

		serviceConfig.setRef(userService);

		serviceConfig.setRegistryConfig(registryConfig);

		serviceConfig.export();

	}

}
