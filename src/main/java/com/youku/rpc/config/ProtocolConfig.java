package com.youku.rpc.config;

import com.youku.rpc.common.IpHelper;
import com.youku.rpc.net.URL;

public class ProtocolConfig {

	private String name;

	private String ip= IpHelper.getLocalIp();;

	private int port;

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

	public URL toURL() {
		return new URL(new StringBuilder().append(ip).append(':').append(port).toString());
	}

}
