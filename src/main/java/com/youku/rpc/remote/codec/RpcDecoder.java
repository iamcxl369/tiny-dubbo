package com.youku.rpc.remote.codec;

import java.util.List;

import org.springframework.util.Assert;

import com.youku.rpc.common.Const;
import com.youku.rpc.factory.SerializerFactory;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.serialize.Serializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RpcDecoder extends ByteToMessageDecoder {

	private final int MAGIC_LENGTH = 2;

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		SimpleByteBuffer buffer = new SimpleByteBuffer(in);

		int readableBytes = buffer.readableBytes();
		if (readableBytes < MAGIC_LENGTH) {
			return;
		}

		buffer.markReaderIndex();

		short magic = buffer.readShort();

		Assert.isTrue(magic == Const.MAGIC);

		int length = buffer.readInt();

		if (readableBytes < length) {
			buffer.resetReaderIndex();
			return;
		}

		String serializerTag = buffer.readLengthAndString();

		String interfaceName = buffer.readLengthAndString();

		String methodName = buffer.readLengthAndString();

		Serializer serializer = SerializerFactory.getSerializer(serializerTag);

		// 请求体
		int size = buffer.readInt();

		Class<?>[] paramTypes = new Class<?>[size];

		for (int i = 0; i < size; i++) {
			byte[] data = buffer.readLengthAndBytes();
			paramTypes[i] = serializer.deserialize(data, Class.class);
		}

		Object[] params = new Object[size];
		for (int i = 0; i < size; i++) {
			byte[] data = buffer.readLengthAndBytes();
			params[i] = serializer.deserialize(data, paramTypes[i]);
		}

		Request request = new Request();

		request.setMethodName(methodName);
		request.setArgumentTypes(paramTypes);
		request.setArguments(params);
		request.setInterfaceName(interfaceName);
		request.setMethodName(methodName);

		out.add(request);

	}

}
