package com.youku.rpc.remote.server.impl;

import com.youku.rpc.remote.codec.RpcDecoder;
import com.youku.rpc.remote.codec.RpcEncoder;
import com.youku.rpc.remote.server.RpcServerHandler;
import com.youku.rpc.remote.server.Server;
import com.youku.rpc.remote.support.URL;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class NettyServer implements Server {

	private URL url;

	private ChannelFuture channelFuture;

	public NettyServer(URL url) {
		this.url = url;
	}

	@Override
	public void open() {
		// boss线程组接受客户端请求信息，并将接收到的信息交给work线程组处理。所以boss group数量设为1即可
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		// work group是实际处理用户请求信息的工作线程组，建议配置N，1<=N<=CPU core
		EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()//
								.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2))//
								.addLast(new RpcDecoder())//
								.addLast(new LengthFieldPrepender(2))//
								.addLast(new RpcEncoder(url))//
								.addLast(new RpcServerHandler());

					}
				}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

		channelFuture = b.bind(url.getIp(), url.getPort());
	}

	@Override
	public void close() {
		channelFuture.channel().close();
	}

}
