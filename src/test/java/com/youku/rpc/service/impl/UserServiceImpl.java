package com.youku.rpc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.model.User;
import com.youku.rpc.service.UserService;

public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public void register(User user) {
		log.info("学生{}注册", user);
	}

	@Override
	public List<User> filter(List<User> users) {
		log.info("过滤不合法用户");

		List<User> newUsers = new ArrayList<>(users.size());

		for (User user : users) {

			if (user.getId() > 0) {
				newUsers.add(user);
			}
		}
		return newUsers;
	}

	@Override
	public boolean isExist(List<User> existUsers, User unknown) {
		log.info("查找用户是否存在");
		for (User user : existUsers) {
			if (user.equals(unknown)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean checkNameExist(Map<String, User> existUsers, String name) {
		log.info("查找用户名字是否存在");
		return existUsers.containsKey(name);
	}

}
