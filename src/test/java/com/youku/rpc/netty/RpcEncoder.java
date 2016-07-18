package com.youku.rpc.netty;

import com.youku.rpc.model.User;
import com.youku.rpc.remote.serialize.Serializer;
import com.youku.rpc.remote.serialize.impl.KryoSerializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcEncoder extends MessageToByteEncoder<Object> {

	public static final Serializer serializer = new KryoSerializer();

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
//		if(out!=null)
//			throw new RuntimeException();
		User u = (User) msg;
		out.writeBytes(serializer.serialize(u));
	}

}
