package com.youku.rpc.netty;

import com.youku.rpc.remote.support.URL;

public class VariableGenerator {

	public static String URLString() {
		return "localhost:8080?serializer=kryo&connect_timeout=10";
	}

	public static URL url() {
		return new URL(URLString());
	}
}
