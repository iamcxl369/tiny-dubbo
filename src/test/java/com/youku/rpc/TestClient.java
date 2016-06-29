package com.youku.rpc;

import java.util.ArrayList;
import java.util.List;

import com.youku.rpc.config.ApplicationConfig;
import com.youku.rpc.config.ReferenceConfig;
import com.youku.rpc.config.RegistryConfig;
import com.youku.rpc.model.User;
import com.youku.rpc.service.UserService;

public class TestClient {

	public static void main(String[] args) {
		ApplicationConfig applicationConfig = new ApplicationConfig();

		applicationConfig.setName("hello-app-consumer");
		applicationConfig.setOwner("loda");
		RegistryConfig registryConfig = new RegistryConfig(TestConsts.ZK_REGISTRY_ADDRESS);

		ReferenceConfig<UserService> reference = new ReferenceConfig<UserService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
		reference.setApplicationConfig(applicationConfig);
		reference.setRegistryConfig(registryConfig);// 多个注册中心可以用setRegistries()
		reference.setInterfaceClass(UserService.class);
		reference.createCluster("failover");
		reference.createLoadBalance("random");

		// reference.setUrl(new URL("10.10.23.92:" + TestConsts.SERVER_PORT));

		UserService userService = reference.get();

		// userService.register(new User(1, "jack"));

		List<User> users = new ArrayList<>();

		users.add(new User(1, "jack"));
		users.add(new User(2, "tom"));
		users.add(new User(-1, "hello"));
		List<User> newUsers = userService.filter(users);

		System.out.println("合法用户为:" + newUsers);
	}
}
