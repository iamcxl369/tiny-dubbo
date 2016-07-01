package com.youku.rpc.remote.codec;

import com.youku.rpc.common.Const;
import com.youku.rpc.factory.SerializerFactory;
import com.youku.rpc.remote.URL;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcEncoder extends MessageToByteEncoder<Object> {

	private URL url;

	public RpcEncoder(URL url) {
		super();
		this.url = url;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		byte[] data = SerializerFactory.getSerializer(url.getParam(Const.SERIALIZER)).serialize(msg);
		out.writeInt(data.length);
		out.writeBytes(data);
	}

}
