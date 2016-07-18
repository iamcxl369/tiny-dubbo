package com.youku.rpc.netty;

import java.util.List;

import com.youku.rpc.model.User;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RpcDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		int size = in.readableBytes();
		byte[] dst = new byte[size];
		in.readBytes(dst);
		User user = RpcEncoder.serializer.deserialize(dst);
		out.add(user);
	}

}
