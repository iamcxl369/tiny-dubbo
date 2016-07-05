package com.youku.rpc.remote.codec;

import io.netty.buffer.ByteBuf;

public class SimpleByteBuffer {

	private ByteBuf byteBuf;

	public SimpleByteBuffer(ByteBuf byteBuf) {
		super();
		this.byteBuf = byteBuf;
	}

	public boolean readBoolean() {
		return byteBuf.readBoolean();
	}

	public byte readByte() {
		return byteBuf.readByte();
	}

	public short readShort() {
		return byteBuf.readShort();
	}

	public int readInt() {
		return byteBuf.readInt();
	}

	public long readLong() {
		return byteBuf.readLong();
	}

	public String readString(int length) {
		byte[] dst = new byte[length];
		readBytes(dst);
		return new String(dst);
	}

	public String readLengthAndString() {
		int len = readInt();
		return readString(len);
	}

	public SimpleByteBuffer readBytes(byte[] dst) {
		byteBuf.readBytes(dst);
		return this;
	}

	public byte[] readLengthAndBytes() {
		int length = readInt();
		byte[] data = new byte[length];
		readBytes(data);
		return data;
	}

	public int readableBytes() {
		return byteBuf.readableBytes();
	}

	public int readerIndex() {
		return byteBuf.readerIndex();
	}

	public SimpleByteBuffer readerIndex(int index) {
		byteBuf.readerIndex(index);
		return this;
	}

	public SimpleByteBuffer markReaderIndex() {
		byteBuf.markReaderIndex();
		return this;
	}

	public SimpleByteBuffer resetReaderIndex() {
		byteBuf.resetReaderIndex();
		return this;
	}

	public int writerIndex() {
		return byteBuf.writerIndex();
	}

	public SimpleByteBuffer writerIndex(int index) {
		byteBuf.writerIndex(index);
		return this;
	}

	public SimpleByteBuffer markWriterIndex() {
		byteBuf.markWriterIndex();
		return this;
	}

	public SimpleByteBuffer resetWriterIndex() {
		byteBuf.resetWriterIndex();
		return this;
	}

	public SimpleByteBuffer writeByte(int value) {
		byteBuf.writeByte(value);
		return this;
	}

	public SimpleByteBuffer writeShort(int value) {
		byteBuf.writeShort(value);
		return this;
	}

	public SimpleByteBuffer writeInt(int value) {
		byteBuf.writeInt(value);
		return this;
	}

	public SimpleByteBuffer writeLong(int value) {
		byteBuf.writeLong(value);
		return this;
	}

	public SimpleByteBuffer writeString(String value) {
		byte[] bytes = value.getBytes();
		writeBytes(bytes);
		return this;
	}

	public SimpleByteBuffer writeLengthAndString(String value) {
		byte[] bytes = value.getBytes();
		byteBuf.writeInt(bytes.length);
		writeBytes(bytes);
		return this;
	}

	public SimpleByteBuffer writeBytes(byte[] src) {
		byteBuf.writeBytes(src);
		return this;
	}

	public SimpleByteBuffer writeLengthAndBytes(byte[] src) {
		writeInt(src.length);
		writeBytes(src);
		return this;
	}

}
