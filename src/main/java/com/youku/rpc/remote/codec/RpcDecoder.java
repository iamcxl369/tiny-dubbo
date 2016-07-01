package com.youku.rpc.remote.codec;

import java.util.List;

import com.youku.rpc.common.Const;
import com.youku.rpc.factory.SerializerFactory;
import com.youku.rpc.remote.URL;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RpcDecoder extends ByteToMessageDecoder {

	private final int HEAD_LENGTH = 4;

	private Class<?> targetClass;

	private URL url;

	public RpcDecoder(URL url, Class<?> targetClass) {
		this.url = url;
		this.targetClass = targetClass;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		if (in.readableBytes() < HEAD_LENGTH) {
			return;
		}

		in.markReaderIndex();

		int dataLen = in.readInt();

		if (dataLen < 0) {
			ctx.close();
		}

		if (in.readableBytes() < dataLen) {
			in.resetReaderIndex();
			return;
		}

		byte[] data = new byte[dataLen];

		in.readBytes(data);

		Object obj = SerializerFactory.getSerializer(url.getParam(Const.SERIALIZER)).deserialize(data, targetClass);

		out.add(obj);

	}

}
