package com.youku.rpc.client.impl;

import java.util.concurrent.TimeUnit;

import com.youku.rpc.client.Client;
import com.youku.rpc.client.Request;
import com.youku.rpc.client.RpcClientHandler;
import com.youku.rpc.common.Const;
import com.youku.rpc.common.Progress;
import com.youku.rpc.net.URL;
import com.youku.rpc.server.Response;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyClient implements Client {

	private URL url;

	private ChannelFuture channelFuture;

	private RpcClientHandler handler;

	public NettyClient(URL url) {
		this.url = url;
	}

	@Override
	public void open() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		handler = new RpcClientHandler();

		Bootstrap b = new Bootstrap(); // (1)
		b.group(workerGroup); // (2)
		b.channel(NioSocketChannel.class); // (3)
		b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
		b.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline()//
						.addLast(new ObjectEncoder())//
						.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))//
						.addLast(handler);
			}
		});

		channelFuture = b.connect(url.getIp(), url.getPort());
		boolean success = channelFuture.awaitUninterruptibly(Const.CONNECT_TIME_OUT, TimeUnit.SECONDS);

		if (!success || !channelFuture.isSuccess()) {
			throw new RuntimeException("连接" + url.getIp() + ":" + url.getPort() + "超时");
		}
	}

	@Override
	public void close() {
		channelFuture.channel().close();
	}

	@Override
	public Response send(Request request) {
		boolean success = channelFuture.channel().writeAndFlush(request).awaitUninterruptibly(Const.TIME_OUT,
				TimeUnit.SECONDS);

		if (success && channelFuture.isSuccess()) {
			Progress.getInstance().process(Const.TIME_OUT, TimeUnit.SECONDS);

			if (handler.getResponse() == null) {
				throw new RuntimeException("没有获取到远程机器的执行结果");
			} else {
				return handler.getResponse();
			}
		} else {
			throw new RuntimeException("rpc请求超时");
		}

	}

	public static void main(String[] args) {
		Client client = new NettyClient(new URL("10.10.23.91", 8080));
		client.open();
		client.send(new Request());
	}
}
