package com.youku.rpc.config;

import com.youku.rpc.common.IpHelper;
import com.youku.rpc.net.URL;

public class ProtocolConfig {

	private URL url;

	public ProtocolConfig(String ip, int port) {
		super();
		this.url = new URL(ip + ":" + port);
	}

	public ProtocolConfig(int port) {
		this(getLocalIp(), port);
	}

	private static String getLocalIp() {
		return IpHelper.getLocalIp();
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

}
