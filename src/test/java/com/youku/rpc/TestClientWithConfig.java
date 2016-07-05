package com.youku.rpc;

import java.util.ArrayList;
import java.util.List;

import com.youku.rpc.bootstrap.Nevermore;
import com.youku.rpc.model.User;
import com.youku.rpc.service.UserService;

public class TestClientWithConfig {

	public final static String consumer = "C:/Users/loda/git/minRpc/src/main/resources/consumer.xml";

	public static void main(String[] args) {
		Nevermore nevermore = new Nevermore(consumer);

		nevermore.start();

		UserService userService = nevermore.getBean("userService");

		List<User> users = new ArrayList<>();

		users.add(new User(1, "jack"));
		users.add(new User(2, "tom"));
		users.add(new User(-1, "hello"));
		List<User> newUsers = userService.filter(users);
		
//		userService.register(new User(1, "jack"));

		System.out.println("合法用户为:" + newUsers);
	}
}
