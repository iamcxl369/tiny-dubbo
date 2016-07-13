package com.youku.rpc.remote.client;

import org.springframework.util.Assert;

import com.youku.rpc.common.Progress;
import com.youku.rpc.remote.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcClientHandler extends SimpleChannelInboundHandler<Response> {

	private Response response;

	private Progress progress;

	public void setProgress(Progress progress) {
		Assert.notNull(progress);
		this.progress = progress;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {
		this.response = response;
		progress.done();
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
