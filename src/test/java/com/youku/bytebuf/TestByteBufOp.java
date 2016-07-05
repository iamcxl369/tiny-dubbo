package com.youku.bytebuf;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

public class TestByteBufOp {

	@Test
	public void testOp() {
		ByteBuf buf = new PooledByteBufAllocator().buffer();

		int start = buf.writerIndex();

		String word = "hello,world";

		buf.writeInt(word.getBytes().length);
		buf.writeBytes(word.getBytes());

		int end = buf.writerIndex();

		System.out.printf("start is %d and end is %d\n", start, end);

		int len = buf.readInt();

		byte[] s = new byte[len];

		buf.readBytes(s);

		System.out.printf("sting is %s\n", new String(s));
	}
	
}
