package com.youku.rpc.remote.serialize;

public interface Serializer {

	byte[] serialize(Object obj);

	<T> T deserialize(byte[] data);

}
