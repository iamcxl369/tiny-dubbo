package com.youku.rpc.remote.codec;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.common.Const;
import com.youku.rpc.factory.SerializerFactory;
import com.youku.rpc.remote.serialize.Serializer;
import com.youku.rpc.remote.support.BaseMessage;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.Response;
import com.youku.rpc.remote.support.URL;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * message:head+body
 * 
 * head: magic+length+type+attachment
 * 
 * body:interfaceName+methodName+size+classType+params
 * 
 * attachment:id{k,v},serializer{k,v}
 * 
 * @author loda
 *
 */
public class RpcEncoder extends MessageToByteEncoder<Object> {

	private static final Logger log = LoggerFactory.getLogger(RpcEncoder.class);

	private URL url;

	public RpcEncoder(URL url) {
		super();
		this.url = url;
	}

	@Override
	public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		SimpleByteBuffer buffer = new SimpleByteBuffer(out);
		if (msg instanceof Request) {
			encodeRequest(ctx, msg, buffer);
		} else if (msg instanceof Response) {
			encodeResponse(ctx, msg, buffer);
		} else {
			throw new IllegalArgumentException("不支持类型为" + msg.getClass() + "的参数");
		}

	}

	private String getSerializerName() {
		return url.getParam(Const.SERIALIZER_KEY);
	}

	private Serializer getSerializer() {
		return SerializerFactory.getSerializer(getSerializerName());
	}

	private void encodeRequest(ChannelHandlerContext ctx, Object msg, SimpleByteBuffer buffer) throws Exception {
		Request request = (Request) msg;
		log.debug("编码request:{}", request);
		Serializer serializer = getSerializer();
		// 消息头
		encodeHeader(buffer, request);

		// 消息体：接口类型+方法名+参数类型+参数值
		buffer.writeLengthAndBytes(serializer.serialize(request.getInterfaceName()));
		buffer.writeLengthAndBytes(serializer.serialize(request.getMethodName()));

		buffer.writeInt(request.getArgumentTypes().length);

		for (Class<?> paramClass : request.getArgumentTypes()) {
			byte[] data = serializer.serialize(paramClass.getName());
			buffer.writeLengthAndBytes(data);
		}

		for (Object param : request.getArguments()) {
			byte[] data = serializer.serialize(param);
			buffer.writeLengthAndBytes(data);
		}

	}

	private void encodeHeader(SimpleByteBuffer buffer, BaseMessage message) {
		buffer.writeShort(Const.MAGIC);
		Map<String, String> attachment = new HashMap<>();
		buffer.writeByte(message.getType());
		attachment.put(Const.ID_KEY, message.getId());
		attachment.put(Const.SERIALIZER_KEY, getSerializerName());

		buffer.writeInt(attachment.size());

		for (Entry<String, String> param : attachment.entrySet()) {
			buffer.writeLengthAndString(param.getKey());
			buffer.writeLengthAndString(param.getValue());
		}
	}

	private void encodeResponse(ChannelHandlerContext ctx, Object msg, SimpleByteBuffer buffer) {
		Response response = (Response) msg;
		log.debug("编码response:{}", response);
		Serializer serializer = getSerializer();
		// 消息头
		encodeHeader(buffer, response);

		// 消息体 返回值
		buffer.writeLengthAndBytes(serializer.serialize(response.getValue()));
	}

}
