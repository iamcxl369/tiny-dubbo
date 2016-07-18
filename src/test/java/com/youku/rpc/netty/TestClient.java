package com.youku.rpc.netty;

import com.youku.rpc.exception.RpcException;
import com.youku.rpc.model.User;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class TestClient {

	public static void main(String[] args) throws RpcException, InterruptedException {
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		Bootstrap b = new Bootstrap();
		b.group(workerGroup)//
				.channel(NioSocketChannel.class)//
				.option(ChannelOption.SO_KEEPALIVE, true)//
				.option(ChannelOption.TCP_NODELAY, true)//
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()//
								.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2))//
								.addLast(new RpcDecoder())//
								.addLast(new LengthFieldPrepender(2))//
								.addLast(new RpcEncoder())//
								.addLast(new ClientHandler());
					}
				});

		ChannelFuture f = b.connect("localhost", 8080).sync();

		for (int i = 0; i < 10; i++) {
			User user = new User(i, "haha" + i);
			f.channel().writeAndFlush(user);

		}
	}

	private static class ClientHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			User s = (User) msg;
			System.out.println("client:" + s);

		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
			cause.printStackTrace();
		}

	}

}
