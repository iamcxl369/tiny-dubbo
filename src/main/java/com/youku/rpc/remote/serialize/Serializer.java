package com.youku.rpc.remote.serialize;

public interface Serializer {

	public byte[] serialize(Object obj);

	public <T> T deserialize(byte[] data, Class<T> targetClass);
}
