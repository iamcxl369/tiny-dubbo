package com.youku.rpc.remote.client;

import com.youku.rpc.remote.support.DefaultFuture;
import com.youku.rpc.remote.support.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcClientHandler extends SimpleChannelInboundHandler<Response> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {
		DefaultFuture.received(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
