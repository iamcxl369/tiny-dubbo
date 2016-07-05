package com.youku.rpc.remote.codec;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		int crcCode = in.readInt();

		int length = in.readInt();

		byte type = in.readByte();

		byte priority = in.readByte();

		int interfaceNameLen = in.readInt();
		byte[] interfaceNameData = new byte[interfaceNameLen];
		in.readBytes(interfaceNameData);
		String interfaceName = new String(interfaceNameData);

		int methodNameLen = in.readInt();
		byte[] methodNameData = new byte[methodNameLen];
		in.readBytes(methodNameData);
		String methodName = new String(methodNameData);

		Map<String, String> attachment = new HashMap<>();
		int size = in.readInt();

		for (int i = 0; i < size; i++) {
			int keyLen = in.readInt();
			byte[] keyData = new byte[keyLen];
			in.readBytes(keyData);
			String key = new String(keyData);

			int valueLen = in.readInt();
			byte[] valueData = new byte[valueLen];
			in.readBytes(valueData);
			String value = new String(valueData);

			attachment.put(key, value);
		}

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
