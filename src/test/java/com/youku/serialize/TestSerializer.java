package com.youku.serialize;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.youku.rpc.model.User;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.serialize.Serializer;
import com.youku.rpc.remote.serialize.impl.FastjsonSerializer;
import com.youku.rpc.remote.serialize.impl.JacksonSerializer;
import com.youku.rpc.remote.serialize.impl.JavaSerializer;
import com.youku.rpc.service.UserService;
import com.youku.rpc.service.impl.UserServiceImpl;

public class TestSerializer {

	Serializer serializer;

	Request request;

	@Before
	public void init() {
		request = new Request();
		request.setRef(new UserServiceImpl());
		request.setMethodName("filter");
		request.setInterfaceClass(UserService.class);
		request.setArgumentTypes(new Class<?>[]{List.class});
		request.setArguments(new Object[]{Arrays.asList(new User[]{new User(1, "jack")})});
	}

	@Test
	public void testJavaSerializer() {
		serializer = new JavaSerializer();
		execute();
	}

	@Test
	public void testFastjsonSerializer() {
		serializer = new FastjsonSerializer();
		execute();
	}

	@Test
	public void testJacksonSerializer() {
		serializer = new JacksonSerializer();
		execute();
	}

	public void execute() {
		byte[] data = serializer.serialize(request);

		Request p = serializer.deserialize(data, Request.class);

		p.setRef(new UserServiceImpl());
		p.invoke();
		System.out.println(p);
	}
}
