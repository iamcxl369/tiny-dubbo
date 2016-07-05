package com.youku.serialize;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.youku.rpc.model.User;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.serialize.Serializer;
import com.youku.rpc.remote.serialize.impl.FastjsonSerializer;
import com.youku.rpc.remote.serialize.impl.JacksonSerializer;
import com.youku.rpc.remote.serialize.impl.JavaSerializer;
import com.youku.rpc.remote.serialize.impl.KryoSerializer;
import com.youku.rpc.service.impl.UserServiceImpl;

public class TestSerializer {

	Serializer serializer;

	// Request request;

	List<User> users;

	@Before
	public void init() {
		users = new ArrayList<>();
		users.add(new User(1, "jack"));
		users.add(new User(2, "tom"));
		// request = new Request();
		// request.setRef("");
		// // request.setMethodName("filter");
		// request.setMethodName("register");
		// request.setInterfaceName(UserService.class.getName());
		// request.setArgumentTypes(new Class<?>[] { User.class });
		// // request.setArguments(new Object[] { Arrays.asList(new User(1,
		// // "jack")) });
		// request.setArguments(new Object[] { new User(1, "jack") });
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

	@Test
	public void testKryoSerializer() {
		serializer = new KryoSerializer();
		execute();
	}

	public void execute() {
		byte[] data = serializer.serialize(users);

		List<User> p = serializer.deserialize(data);

		System.out.println(p.get(0).getClass());
	}

}
