package com.youku.rpc;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import com.youku.rpc.bootstrap.Nevermore;
import com.youku.rpc.model.User;
import com.youku.rpc.service.UserService;
import com.youku.util.SystemUtils;

public class TestClientWithConfig {

	private final static String consumer = SystemUtils.getBasePath() + File.separator + "consumer.xml";

	public static void main(String[] args) {
		Nevermore nevermore = new Nevermore(consumer);

		nevermore.start();

		UserService userService = nevermore.getBean("userService");

		List<User> users = new ArrayList<>();

		users.add(new User(1, "jack"));
		users.add(new User(2, "tom"));
		users.add(new User(-1, "hello"));

		long start = System.currentTimeMillis();

		List<User> newUsers = null;
		for (int i = 0; i < 100; i++) {
			newUsers = userService.filter(users);
		}

		System.out.printf("cost %d ms and result is \n%s\n", System.currentTimeMillis() - start, newUsers);

		// userService.register(new User(1, "jack"));
		// System.out.println(userService.isExist(users, new User(2, "jack")));

		// Map<String, User> userMap = new HashMap<>();
		//
		// for (User user : users) {
		// userMap.put(user.getName(), user);
		// }
		//
		// boolean exist = userService.checkNameExist(userMap, "tom");
		//
		// System.out.println(exist ? "存在" : "不存在");
	}
}
