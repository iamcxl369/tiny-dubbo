package com.youku.rpc.remote.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youku.rpc.common.Const;
import com.youku.rpc.factory.SerializerFactory;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.Response;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.serialize.Serializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * message:head+body
 * 
 * head: magic+length+type+serializer
 * 
 * body:interfaceName+methodName+size+classType+params
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
		if (msg instanceof Request) {
			encodeRequest(ctx, msg, out);
		} else if (msg instanceof Response) {
			encodeResponse(ctx, msg, out);
		} else {
			throw new IllegalArgumentException("不支持类型为" + msg.getClass() + "的参数");
		}

	}

	private String getSerializerName() {
		return url.getParam(Const.SERIALIZER);
	}

	private Serializer getSerializer() {
		return SerializerFactory.getSerializer(getSerializerName());
	}

	private void encodeRequest(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
		Request request = (Request) msg;
		log.info("编码request:{}", request);
		Serializer serializer = getSerializer();
		SimpleByteBuffer buffer = new SimpleByteBuffer(out);

		// 消息头
		int start = buffer.writerIndex();
		buffer.writeShort(Const.MAGIC);
		int mark = buffer.writerIndex();
		buffer.writerIndex(mark + 4);
		buffer.writeByte(Const.REQUEST);
		buffer.writeLengthAndString(getSerializerName());

		// 消息体：接口类型+方法名+参数类型+参数值
		buffer.writeLengthAndBytes(serializer.serialize(request.getInterfaceClass()));
		buffer.writeLengthAndBytes(serializer.serialize(request.getMethodName()));

		buffer.writeInt(request.getArgumentTypes().length);

		for (Class<?> paramClass : request.getArgumentTypes()) {
			byte[] data = serializer.serialize(paramClass);
			buffer.writeLengthAndBytes(data);
		}

		for (Object param : request.getArguments()) {
			byte[] data = serializer.serialize(param);
			buffer.writeLengthAndBytes(data);
		}

		int eof = buffer.writerIndex();

		buffer.writerIndex(mark);

		buffer.writeInt(eof - start);

		buffer.writerIndex(eof);
	}

	private void encodeResponse(ChannelHandlerContext ctx, Object msg, ByteBuf out) {
		Response response = (Response) msg;
		log.info("编码response:{}", response);
		Serializer serializer = getSerializer();
		SimpleByteBuffer buffer = new SimpleByteBuffer(out);

		// 消息头
		int start = buffer.writerIndex();
		buffer.writeShort(Const.MAGIC);
		int mark = buffer.writerIndex();
		buffer.writerIndex(mark + 4);
		buffer.writeByte(Const.RESPONSE);
		buffer.writeLengthAndString(getSerializerName());

		// 消息体 返回值
		buffer.writeLengthAndBytes(serializer.serialize(response.getValue()));

		int eof = buffer.writerIndex();

		buffer.writerIndex(mark);

		buffer.writeInt(eof - start);

		buffer.writerIndex(eof);
	}

}
