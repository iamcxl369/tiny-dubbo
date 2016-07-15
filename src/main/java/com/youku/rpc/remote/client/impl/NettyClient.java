package com.youku.rpc.remote.client.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.common.Const;
import com.youku.rpc.common.Progress;
import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.client.Client;
import com.youku.rpc.remote.client.RpcClientHandler;
import com.youku.rpc.remote.codec.RpcDecoder;
import com.youku.rpc.remote.codec.RpcEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient implements Client {

	private static final Logger log = LoggerFactory.getLogger(NettyClient.class);

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

		Bootstrap b = new Bootstrap();
		b.group(workerGroup)//
				.channel(NioSocketChannel.class)//
				.option(ChannelOption.SO_KEEPALIVE, true)//
				.option(ChannelOption.TCP_NODELAY, true)//
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()//
								.addLast(new RpcEncoder(url))//
								.addLast(new RpcDecoder())//
								.addLast(handler);
					}
				});

		channelFuture = b.connect(url.getIp(), url.getPort());
		boolean success = channelFuture.awaitUninterruptibly(Const.CONNECT_TIME_OUT, TimeUnit.MILLISECONDS);

		if (!success || !channelFuture.isSuccess()) {
			throw new RuntimeException("连接" + url.getIp() + ":" + url.getPort() + "超时");
		}
	}

	@Override
	public void close() {
		channelFuture.channel().close();
	}

	@Override
	public Response send(Request request) throws RpcException {
		log.info("客户端发送消息");
		boolean success = channelFuture.channel().writeAndFlush(request).awaitUninterruptibly(Const.TIME_OUT,
				TimeUnit.MILLISECONDS);

		if (success && channelFuture.isSuccess()) {
			Progress progress = new Progress();
			handler.setProgress(progress);
			progress.process();

			if (handler.getResponse() == null) {
				throw new RpcException("没有获取到远程机器的执行结果");
			} else {
				return handler.getResponse();
			}
		} else {
			throw new RpcException("rpc请求超时");
		}

	}

}
