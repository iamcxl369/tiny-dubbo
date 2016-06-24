package com.youku.rpc.server.impl;

import com.youku.rpc.net.URL;
import com.youku.rpc.server.RpcServerHandler;
import com.youku.rpc.server.Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyServer implements Server {

	private URL url;

	private ChannelFuture channelFuture;

	public NettyServer(URL url) {
		this.url = url;
	}

	@Override
	public void open() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap(); // (2)
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)
				.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()//
								.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))//
								.addLast(new ObjectEncoder())//
								.addLast(new RpcServerHandler());

					}
				}).option(ChannelOption.SO_BACKLOG, 128) // (5)
				.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

		channelFuture = b.bind(url.getIp(), url.getPort());
	}

	@Override
	public void close() {
		channelFuture.channel().close();
	}

	public static void main(String[] args) {
		new NettyServer(new URL("10.10.23.91", 8080)).open();
	}

}
