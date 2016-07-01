package com.youku.rpc.config;

import com.youku.rpc.common.IpHelper;
import com.youku.rpc.remote.URL;

public class ProtocolConfig {

	private String name;

	private String ip = IpHelper.getLocalIp();

	private int port;

	private String serializer = "java";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSerializer() {
		return serializer;
	}

	public void setSerializer(String serializer) {
		this.serializer = serializer;
	}

	public URL toURL() {
		return new URL(new StringBuilder().append(ip).append(':').append(port).append("?serializer=").append(serializer)
				.toString());
	}

}
