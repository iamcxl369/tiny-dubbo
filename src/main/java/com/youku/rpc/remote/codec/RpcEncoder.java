package com.youku.rpc.remote.codec;

import com.youku.rpc.common.Const;
import com.youku.rpc.factory.SerializerFactory;
import com.youku.rpc.remote.Request;
import com.youku.rpc.remote.URL;
import com.youku.rpc.remote.serialize.Serializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * message:head+body
 * 
 * head: magic+length+serializer+interfaceName+methodName
 * 
 * body:size+classType+params
 * 
 * @author loda
 *
 */
public class RpcEncoder extends MessageToByteEncoder<Object> {

	private URL url;

	public RpcEncoder(URL url) {
		super();
		this.url = url;
	}

	@Override
	public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		Request request = (Request) msg;
		String serializerTag = url.getParam(Const.SERIALIZER);
		Serializer serializer = SerializerFactory.getSerializer(serializerTag);
		String interfaceName = request.getInterfaceName();
		String methodName = request.getMethodName();

		SimpleByteBuffer buffer = new SimpleByteBuffer(out);

		// 消息头
		int start = buffer.writerIndex();
		buffer.writeShort(Const.MAGIC);
		int mark = buffer.writerIndex();
		buffer.writerIndex(mark + 4);

		buffer.writeLengthAndString(serializerTag);
		buffer.writeLengthAndString(interfaceName);
		buffer.writeLengthAndString(methodName);

		// 消息体：参数类型+参数值
		out.writeInt(request.getArgumentTypes().length);

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

}
