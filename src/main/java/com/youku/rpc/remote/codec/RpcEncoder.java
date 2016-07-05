package com.youku.rpc.remote.codec;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.youku.rpc.common.Const;
import com.youku.rpc.factory.SerializerFactory;
import com.youku.rpc.remote.Request;
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
		// 消息头 header TODO
		Request request = (Request) msg;
		String interfaceName = request.getInterfaceName();
		String methodName = request.getMethodName();
		byte[] interfaceNameData = interfaceName.getBytes();
		byte[] methodNameData = methodName.getBytes();
		byte[] body = SerializerFactory.getSerializer(url.getParam(Const.SERIALIZER)).serialize(msg);

		int index = out.writerIndex();
		out.writeInt(Const.MAGIC);

		out.writeInt(interfaceNameData.length);

		out.writeInt(methodNameData.length);
		out.writeBytes(methodNameData);

		// 消息体
		out.writeBytes(body);
	}

}
