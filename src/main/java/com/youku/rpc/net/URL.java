package com.youku.rpc.net;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public class URL {

	private String ip;

	private int port;

	private String urlString;

	public URL(String urlString) {
		this.urlString = urlString;
		String[] arr = StringUtils.split(urlString, ':');
		
		Assert.isTrue(arr.length==2, "url字符串的格式为ip:port，实际url=>"+urlString+"不合法");
		
		this.ip=arr[0];
		this.port=Integer.parseInt(arr[1]);
	}

	public URL(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
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

	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	@Override
	public String toString() {
		return "url[" + ip + ":" + port + "]";
	}
}
