package com.youku.type;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.youku.rpc.model.User;

public class TestType {

	@Test
	public void testType(){
		List<User> users=new ArrayList<User>();
		
		System.out.println(users.getClass());
	}
}
