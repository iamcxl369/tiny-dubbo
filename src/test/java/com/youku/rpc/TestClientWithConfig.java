package com.youku.rpc;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import com.youku.rpc.bootstrap.Nevermore;
import com.youku.rpc.model.User;
import com.youku.rpc.service.UserService;
import com.youku.util.SystemUtils;

public class TestClientWithConfig {

	private final static String consumer = SystemUtils.macBasePath + File.separator + "consumer.xml";

	public static void main(String[] args) {
		Nevermore nevermore = new Nevermore(consumer);

		nevermore.start();

		UserService userService = nevermore.getBean("userService");

		List<User> users = new ArrayList<>();

		users.add(new User(1, "jack"));
		users.add(new User(2, "tom"));
		users.add(new User(-1, "hello"));

		List<User> newUsers = userService.filter(users);
		System.out.println("合法用户为:" + newUsers);

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
