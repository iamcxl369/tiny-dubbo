package com.youku.rpc.remote.server;

import org.springframework.util.Assert;

import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcServerHandler extends SimpleChannelInboundHandler<Request> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {

		request = initForServer(request);
		
		System.out.println("request===================\n"+request);

		Object result = request.invoke();

		Response response = new Response();

		response.setValue(result);

		ctx.writeAndFlush(response);

		ctx.close();

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	private Request initForServer(Request request) {
		if (request.getRef() == null) {

			Object obj = TypeObjectMapper.get(request.getInterfaceClass());

			Assert.notNull(obj, "没有找到接口类型" + request.getInterfaceClass() + "对应的实现类");
			request.setRef(obj);

		}
		return request;
	}

}
