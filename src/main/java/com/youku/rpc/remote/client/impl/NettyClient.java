package com.youku.rpc.remote.client.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.youku.rpc.common.Const;
import com.youku.rpc.remote.client.Client;
import com.youku.rpc.remote.client.RpcClientHandler;
import com.youku.rpc.remote.codec.RpcDecoder;
import com.youku.rpc.remote.codec.RpcEncoder;
import com.youku.rpc.remote.support.DefaultFuture;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.ResponseFuture;
import com.youku.rpc.remote.support.URL;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class NettyClient implements Client {

	private static final Logger log = LoggerFactory.getLogger(NettyClient.class);

	private URL url;

	private ChannelFuture channelFuture;

	public NettyClient(URL url) {
		this.url = url;
	}

	@Override
	public void open() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		Bootstrap b = new Bootstrap();
		b.group(workerGroup)//
				.channel(NioSocketChannel.class)//
				.option(ChannelOption.SO_KEEPALIVE, true)//
				.option(ChannelOption.TCP_NODELAY, true)//
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,
						url.getIntParam(Const.CONNECT_TIMEOUT_KEY, Const.DEFAULT_CONNECT_TIMEOUT))//
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()//
								.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2))//
								.addLast(new RpcDecoder())//
								.addLast(new LengthFieldPrepender(2))//
								.addLast(new RpcEncoder(url))//
								.addLast(new RpcClientHandler());
					}
				});

		channelFuture = b.connect(url.getIp(), url.getPort());

		channelFuture.awaitUninterruptibly();

		Assert.isTrue(channelFuture.isDone());

		if (!channelFuture.isSuccess()) {
			channelFuture.cause().printStackTrace();
		}
	}

	@Override
	public void close() {
		channelFuture.channel().close();
	}

	@Override
	public void send(Request request) {
		log.debug("客户端发送消息");
		channelFuture = channelFuture.channel().writeAndFlush(request);
		channelFuture.addListener(new ChannelFutureListener() {

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (!future.isSuccess()) {
					future.cause().printStackTrace();
					future.channel().close();
				}
			}
		});

	}

	@Override
	public ResponseFuture request(final Request request) {
		ResponseFuture future = new DefaultFuture(url.getIntParam(Const.TIMEOUT_KEY, Const.DEFAULT_TIMEOUT), request);
		send(request);
		return future;
	}

}
