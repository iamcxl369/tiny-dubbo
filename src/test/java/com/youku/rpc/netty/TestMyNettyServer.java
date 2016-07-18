package com.youku.rpc.netty;

import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.server.TypeObjectMapper;
import com.youku.rpc.remote.server.impl.NettyServer;
import com.youku.rpc.service.impl.UserServiceImpl;

public class TestMyNettyServer {

	public static void main(String[] args) {
		new ExtensionLoader();
		TypeObjectMapper.binding("com.youku.rpc.service.UserService", new UserServiceImpl());
		NettyServer server = new NettyServer(new URL("localhost:8080?serializer=kryo"));
		server.open();
	}

}
