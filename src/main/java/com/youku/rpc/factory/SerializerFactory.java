package com.youku.rpc.factory;

import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.remote.serialize.Serializer;

public class SerializerFactory {

	public static Serializer getSerializer(String name) {
		return ExtensionLoader.getExtension(Serializer.class, name);
	}
}
