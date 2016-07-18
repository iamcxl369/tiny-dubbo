package com.youku.rpc.remote.codec;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.youku.rpc.common.Const;
import com.youku.rpc.common.ReflectUtils;
import com.youku.rpc.factory.SerializerFactory;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;
import com.youku.rpc.remote.serialize.Serializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RpcDecoder extends ByteToMessageDecoder {

	private static final Logger log = LoggerFactory.getLogger(RpcDecoder.class);

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		log.info("解码过程开始");
		SimpleByteBuffer buffer = new SimpleByteBuffer(in);

		short magic = buffer.readShort();

		Assert.isTrue(magic == Const.MAGIC);

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
		log.debug("解码response信息");
		Object value = serializer.deserialize(buffer.readLengthAndBytes());
		Response response = new Response();
		response.setValue(value);
		out.add(response);
	}

	private void decodeRequest(SimpleByteBuffer buffer, List<Object> out, Serializer serializer) {
		log.debug("解码request信息");
		// 请求体
		String interfaceName = serializer.deserialize(buffer.readLengthAndBytes());

		String methodName = serializer.deserialize(buffer.readLengthAndBytes());

		int size = buffer.readInt();

		Class<?>[] paramTypes = new Class<?>[size];

		for (int i = 0; i < size; i++) {
			byte[] data = buffer.readLengthAndBytes();
			String className = serializer.deserialize(data);
			paramTypes[i] = ReflectUtils.forName(className);
		}

		Object[] params = new Object[size];
		for (int i = 0; i < size; i++) {
			byte[] data = buffer.readLengthAndBytes();
			params[i] = serializer.deserialize(data);
		}

		Request request = new Request();

		request.setMethodName(methodName);
		request.setArgumentTypes(paramTypes);
		request.setArguments(params);
		request.setInterfaceName(interfaceName);
		request.setMethodName(methodName);

		log.debug("解码完成后request:{}", request);

		out.add(request);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
