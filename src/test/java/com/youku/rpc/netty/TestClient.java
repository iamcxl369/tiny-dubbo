package com.youku.rpc.netty;

import java.util.concurrent.Future;

import com.youku.rpc.exception.RpcException;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
								// .addLast(new RpcEncoder(url))//
								// .addLast(new RpcDecoder())//
								.addLast(new ClientHandler());
					}
				});

		ChannelFuture f = b.connect("localhost", 8080).sync();

		String h = "haha";
		f.channel().writeAndFlush(Unpooled.copiedBuffer(h.getBytes()));
	}

	private static class ClientHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			String s = "hello,world";
			ctx.writeAndFlush(Unpooled.copiedBuffer(s.getBytes()));
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			ByteBuf buf = (ByteBuf) msg;
			byte[] data = new byte[buf.readableBytes()];

			buf.readBytes(data);

			System.out.println("client:" + new String(data));

		}

	}

}
