package com.youku.rpc.remote.codec;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RpcDecoder extends ByteToMessageDecoder {

	private Class<?> targetClass;

	public RpcDecoder(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		int len = in.readableBytes();

		byte[] buff = new byte[len];

		in.readBytes(buff);

		Object obj = new ObjectMapper().readValue(buff, targetClass);

		out.add(obj);

	}

}
