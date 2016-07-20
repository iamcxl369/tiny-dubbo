package com.youku.rpc.remote.codec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.youku.rpc.common.Const;
import com.youku.rpc.common.ReflectUtils;
import com.youku.rpc.factory.SerializerFactory;
import com.youku.rpc.remote.MessageType;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;
import com.youku.rpc.remote.serialize.Serializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * message:head+body
 * 
 * head: magic+length+type+serializer+attachment
 * 
 * body:interfaceName+methodName+size+classType+params
 * 
 * @author loda
 *
 */
public class RpcDecoder extends ByteToMessageDecoder {

	private static final Logger log = LoggerFactory.getLogger(RpcDecoder.class);

	@Override
	public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		SimpleByteBuffer buffer = new SimpleByteBuffer(in);

		short magic = buffer.readShort();

		Assert.isTrue(magic == Const.MAGIC);

		byte type = buffer.readByte();
		MessageType messageType = MessageType.findByType(type);
		Assert.notNull(messageType, "不存在类型为" + type + "的消息");

		int size = buffer.readInt();

		Map<String, String> attachment = new HashMap<>();
		for (int i = 0; i < size; i++) {
			attachment.put(buffer.readLengthAndString(), buffer.readLengthAndString());
		}

		String id = attachment.get(Const.ID_KEY);
		String serializerName = attachment.get(Const.SERIALIZER_KEY);
		Assert.notNull(id, messageType.desc() + "信息中id不能为空");
		Assert.notNull(serializerName, messageType.desc() + "信息中的serializer不能为空");
		Serializer serializer = SerializerFactory.getSerializer(serializerName);

		switch (messageType) {
		case REQUEST:
			decodeRequest(buffer, out, serializer, id);
			break;
		case RESPONSE:
			decodeResponse(buffer, out, serializer, id);
			break;
		default:
			throw new UnsupportedOperationException("尚不支持对" + messageType + "类型请求的解析");
		}
	}

	private void decodeResponse(SimpleByteBuffer buffer, List<Object> out, Serializer serializer, String id) {
		log.debug("解码response信息");

		Response response = new Response(id);
		// 响应体
		Object value = serializer.deserialize(buffer.readLengthAndBytes());
		response.setValue(value);
		out.add(response);
	}

	private void decodeRequest(SimpleByteBuffer buffer, List<Object> out, Serializer serializer, String id) {
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

		Request request = new Request(id);

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
