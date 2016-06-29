package com.youku.rpc;

import java.util.ArrayList;
import java.util.List;

import com.youku.rpc.model.User;
import com.youku.rpc.parser.Nevermore;
import com.youku.rpc.service.UserService;

public class TestClientWithConfig {

	public final static String consumer = "E:/project/workspace/nevermore/src/main/resources/consumer.xml";

	public static void main(String[] args) {
		Nevermore nevermore = new Nevermore(consumer);

		UserService userService = nevermore.getBean("userService");

		List<User> users = new ArrayList<>();

		users.add(new User(1, "jack"));
		users.add(new User(2, "tom"));
		users.add(new User(-1, "hello"));
		List<User> newUsers = userService.filter(users);

		System.out.println("合法用户为:" + newUsers);
	}
}
