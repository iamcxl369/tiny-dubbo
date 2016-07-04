package com.youku.rpc.remote.codec;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
		// 消息头 header TODO
		int crcCode = 0xAFBA;
		byte type = 0;
		byte priority = 0;
		String interfaceName = "";
		String methodName = "";
		Map<String, String> attachment = new HashMap<>();
		byte[] data = SerializerFactory.getSerializer(url.getParam(Const.SERIALIZER)).serialize(msg);

		int headLength = 0;
		int bodyLength = data.length;
		int length = headLength + bodyLength;

		out.writeInt(crcCode);
		out.writeInt(length);
		out.writeByte(type);
		out.writeByte(priority);

		byte[] interfaceNameData = interfaceName.getBytes();
		out.writeInt(interfaceNameData.length);
		out.writeBytes(interfaceNameData);

		byte[] methodNameData = methodName.getBytes();
		out.writeInt(methodNameData.length);
		out.writeBytes(methodNameData);

		out.writeInt(attachment.size());
		for (Entry<String, String> param : attachment.entrySet()) {
			String key = param.getKey();
			String value = param.getValue();

			byte[] keyData = key.getBytes();
			byte[] valueData = value.getBytes();

			out.writeInt(keyData.length);
			out.writeBytes(keyData);
			out.writeInt(valueData.length);
			out.writeBytes(valueData);
		}

		// 消息体
		out.writeBytes(data);
	}

}
