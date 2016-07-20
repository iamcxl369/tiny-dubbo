package com.youku.bytebuf;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.youku.rpc.common.UUIDUtil;
import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.model.User;
import com.youku.rpc.remote.codec.RpcDecoder;
import com.youku.rpc.remote.codec.RpcEncoder;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.URL;
import com.youku.rpc.service.UserService;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

public class TestCoder {

	@Before
	public void init() {
		new ExtensionLoader();
	}

	@Test
	public void testCoder() throws Exception {
		ByteBuf out = new PooledByteBufAllocator().buffer();

		RpcEncoder encoder = new RpcEncoder(new URL("localhost:8080?serializer=fastjson"));

		Request request = new Request(UUIDUtil.uuid());
		request.setMethodName("register");
		request.setInterfaceName(UserService.class.getName());
		request.setArgumentTypes(new Class<?>[] { User.class });
		request.setArguments(new Object[] { new User(1, "jack") });

		encoder.encode(null, request, out);

		RpcDecoder decoder = new RpcDecoder();

		List<Object> list = new ArrayList<>();
		decoder.decode(null, out, list);

		Request r = (Request) list.get(0);
		System.out.println(r.getInterfaceName());
		User user = (User) r.getArguments()[0];

		System.out.println(user.getName());
	}
}
