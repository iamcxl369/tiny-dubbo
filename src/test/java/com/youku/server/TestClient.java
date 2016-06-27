package com.youku.server;

import java.util.ArrayList;
import java.util.List;

import com.youku.rpc.config.ReferenceConfig;
import com.youku.rpc.config.RegistryConfig;
import com.youku.rpc.model.User;
import com.youku.rpc.service.UserService;

public class TestClient {

	public static void main(String[] args) {
		RegistryConfig registryConfig = new RegistryConfig("127.0.0.1:2181");

		ReferenceConfig<UserService> reference = new ReferenceConfig<UserService>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
		reference.setRegistryConfig(registryConfig);// 多个注册中心可以用setRegistries()
		reference.setInterfaceClass(UserService.class);
		reference.setUrl("10.10.23.91:8080");

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
