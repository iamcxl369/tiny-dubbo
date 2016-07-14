package com.youku.serialize;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.youku.rpc.model.User;
import com.youku.rpc.remote.serialize.Serializer;
import com.youku.rpc.remote.serialize.impl.FastjsonSerializer;
import com.youku.rpc.remote.serialize.impl.JavaSerializer;
import com.youku.rpc.remote.serialize.impl.KryoSerializer;
import com.youku.rpc.remote.serialize.impl.ProtobufSerializer;

public class TestSerializer {

	Serializer serializer;

	// Request request;

	List<User> users;

	User user;

	@Before
	public void init() {
		user = new User(23, "jordan");
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
	public void testJavaSerializer() {// 159
		serializer = new JavaSerializer();
		execute();
	}

	@Test
	public void testFastjsonSerializer() {// 116
		serializer = new FastjsonSerializer();
		execute();
	}

	@Test
	public void testKryoSerializer() {// 39
		serializer = new KryoSerializer();
		execute();
	}

	@Test
	public void testProtocolBuffer() {
		serializer = new ProtobufSerializer();
	}

	public void execute() {
		long start = System.currentTimeMillis();
		byte[] data = serializer.serialize(users);

		serializer.deserialize(data);

		long cost = System.currentTimeMillis() - start;

		System.out.printf("cost %d ms and data size is %d bytes\n", cost, data.length);

	}

}
