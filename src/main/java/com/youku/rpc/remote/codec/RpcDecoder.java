package com.youku.rpc.remote.codec;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.youku.rpc.common.Const;
import com.youku.rpc.factory.SerializerFactory;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;
import com.youku.rpc.remote.serialize.Serializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RpcDecoder extends ByteToMessageDecoder {

	private final int MAGIC_LENGTH = 2;

	private static final Logger log = LoggerFactory.getLogger(RpcDecoder.class);

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

		int type = buffer.readByte();

		String serializerName = buffer.readLengthAndString();

		Serializer serializer = SerializerFactory.getSerializer(serializerName);

		if (type == Const.REQUEST) {
			decodeRequest(buffer, out, serializer);
		} else if (type == Const.RESPONSE) {
			decodeResponse(buffer, out, serializer);
		} else {
			throw new IllegalArgumentException("不存在类型为" + type + "的请求");
		}

	}

	private void decodeResponse(SimpleByteBuffer buffer, List<Object> out, Serializer serializer) {
		log.info("解码response信息");
		Object value = serializer.deserialize(buffer.readLengthAndBytes(), Object.class);
		Response response = new Response();
		response.setValue(value);
		out.add(response);
	}

	private void decodeRequest(SimpleByteBuffer buffer, List<Object> out, Serializer serializer) {
		log.info("解码request信息");
		// 请求体
		Class<?> interfaceClass = serializer.deserialize(buffer.readLengthAndBytes(), Class.class);

		String methodName = serializer.deserialize(buffer.readLengthAndBytes(), String.class);

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
		request.setInterfaceClass(interfaceClass);
		request.setMethodName(methodName);
		
		log.info("解码完成后request:{}",request);
		log.info(request.getArguments()[0].getClass().getName());

		out.add(request);
	}

}
