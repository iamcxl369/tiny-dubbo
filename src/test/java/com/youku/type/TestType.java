package com.youku.type;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.youku.rpc.common.ReflectUtils;
import com.youku.rpc.model.User;

public class TestType {

	@Test
	public void testType() {
		List<User> users = new ArrayList<User>();

		System.out.println(users.getClass());
	}

	@Test
	public void testConstruct() throws SecurityException {

		A a = null;
		Class<?> targetClass = ReflectUtils.forName("com.youku.type.A");

		try {
			System.out.println(targetClass.getConstructor(new Class<?>[] {}));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
