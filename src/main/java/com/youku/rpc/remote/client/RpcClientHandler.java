package com.youku.rpc.remote.client;

import com.youku.rpc.common.Progress;
import com.youku.rpc.remote.server.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcClientHandler extends SimpleChannelInboundHandler<Response> {

	private Response response;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {
		this.response = response;
		Progress.getInstance().done();
	}

	public Response getResponse() {
		return response;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
