package com.youku.rpc.remote.codec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ByteProcessor;

public class ImprovedByteBuff {

	private ByteBuf buf;

	public ImprovedByteBuff(ByteBuf buf) {
		super();
		this.buf = buf;
	}

	public byte readByte() {
		return buf.readByte();
	}

	public int readInt() {
		return buf.readInt();
	}

//	public String readString(int len) {
//		return 
//	}
}
