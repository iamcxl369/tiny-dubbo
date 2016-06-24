package com.youku.rpc.config;

import com.youku.rpc.common.IpHelper;

public class ProtocolConfig {

	private String ip;

	private int port;

	public ProtocolConfig(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}

	public ProtocolConfig(int port) {
		this(getLocalIp(), port);
	}

	private static String getLocalIp() {
		return IpHelper.getLocalIp();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
