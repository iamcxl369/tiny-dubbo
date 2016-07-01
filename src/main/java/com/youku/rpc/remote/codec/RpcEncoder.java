package com.youku.rpc.remote.codec;

import java.io.ByteArrayOutputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		mapper.writeValue(os, msg);

		out.writeBytes(os.toByteArray());
	}

}
