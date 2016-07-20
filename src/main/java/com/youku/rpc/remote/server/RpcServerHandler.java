package com.youku.rpc.remote.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcServerHandler extends SimpleChannelInboundHandler<Request> {

	private static final Logger log = LoggerFactory.getLogger(RpcServerHandler.class);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
		log.debug("处理客户端请求信息");

		request = initForServer(request);

		Object result = request.invoke();

		Response response = new Response(request.getId());

		response.setValue(result);

		ctx.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	private Request initForServer(Request request) {
		if (request.getRef() == null) {

			Object obj = TypeObjectMapper.get(request.getInterfaceName());

			Assert.notNull(obj, "没有找到接口类型" + request.getInterfaceName() + "对应的实现类");
			request.setRef(obj);

		}
		return request;
	}

}
