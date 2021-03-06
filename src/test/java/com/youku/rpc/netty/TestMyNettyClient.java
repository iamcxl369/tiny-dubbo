package com.youku.rpc.netty;

import java.util.Arrays;
import java.util.List;

import com.youku.rpc.common.UUIDUtil;
import com.youku.rpc.exception.RpcException;
import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.model.User;
import com.youku.rpc.remote.client.impl.NettyClient;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.Response;

public class TestMyNettyClient {

	public static void main(String[] args) throws RpcException {
		new ExtensionLoader();

		NettyClient client = new NettyClient(VariableGenerator.url());
		client.open();

		Request request = new Request(UUIDUtil.uuid());
		request.setInterfaceName("com.youku.rpc.service.UserService");
		request.setMethodName("filter");
		request.setArguments(new Object[] { Arrays.asList(new User(1, "hi")) });
		request.setArgumentTypes(new Class<?>[] { List.class });

		Response r = null;
		for (int i = 0; i < 10; i++) {
			// r = client.send(request);
		}

		System.out.println(r);
	}

}
