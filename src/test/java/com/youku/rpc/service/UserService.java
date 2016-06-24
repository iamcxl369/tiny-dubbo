package com.youku.rpc.service;

import java.util.List;

import com.youku.rpc.model.User;

public interface UserService {

	public void register(User user);

	public List<User> filter(List<User> users);
}
