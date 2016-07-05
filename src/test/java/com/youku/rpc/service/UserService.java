package com.youku.rpc.service;

import java.util.List;
import java.util.Map;

import com.youku.rpc.model.User;

public interface UserService {

	void register(User user);

	List<User> filter(List<User> users);

	boolean isExist(List<User> existUsers, User unknown);

	boolean checkNameExist(Map<String, User> existUsers, String name);
}
