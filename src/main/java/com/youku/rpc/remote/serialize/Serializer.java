package com.youku.rpc.remote.serialize;

import java.io.InputStream;

public interface Serializer {

	public byte[] serialize(Object obj);

	public <T> T deserialize(InputStream is);
}
