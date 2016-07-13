package com.youku.rpc.remote;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public class URL {

	private String ip;

	private int port;

	private Map<String, String> params = new HashMap<>();

	private String registryProtocol;

	private String registryAddress;

	public URL() {
		super();
	}

	public URL(String urlString) {
		int index = urlString.indexOf('?');
		String ipPortString = null;
		if (index < 0) {
			ipPortString = urlString;
		} else {
			String paramString = urlString.substring(index + 1);

			for (String kv : StringUtils.split(paramString, '&')) {
				if (StringUtils.isNoneBlank(kv)) {
					String[] kvArr = StringUtils.split(kv, '=');

					String key = kvArr[0];
					String value = kvArr[1];

					this.params.put(key, value);
				}
			}
			ipPortString = urlString.substring(0, index);
		}

		String[] arr = StringUtils.split(ipPortString, ':');

		Assert.isTrue(arr.length == 2);

		this.ip = arr[0];
		this.port = Integer.parseInt(arr[1]);

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

	public String getRegistryAddress() {
		return registryAddress;
	}

	public String getRegistryProtocol() {
		return registryProtocol;
	}

	public void setRegistryProtocol(String registryProtocol) {
		this.registryProtocol = registryProtocol;
	}

	public void setRegistryAddress(String registryAddress) {
		this.registryAddress = registryAddress;
	}

	public String toURLString() {
		StringBuilder builder = new StringBuilder();

		builder.append(ip).append(':').append(port);

		if (params.isEmpty()) {
			return builder.toString();
		} else {
			builder.append('?');
			for (Entry<String, String> entry : params.entrySet()) {
				builder.append(entry.getKey()).append('=').append(entry.getValue()).append('&');
			}

			if (builder.toString().endsWith("&")) {
				builder.deleteCharAt(builder.length() - 1);
			}

			return builder.toString();
		}

	}

	public String getParam(String key) {
		return params.get(key);
	}

	public String getParam(String key, String defaultValue) {
		String value = params.get(key);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}

	public int getIntParam(String key) {
		return Integer.parseInt(getParam(key));
	}

	public int getIntParam(String key, int defaultValue) {
		try {
			return Integer.parseInt(getParam(key));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public long getLongParam(String key) {
		return Long.parseLong(getParam(key));
	}

	public long getLongParam(String key, long defaultValue) {
		try {
			return Long.parseLong(getParam(key));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public double getDoubleParam(String key) {
		return Double.parseDouble(getParam(key));
	}

	public double getDoubleParam(String key, double defaultValue) {
		try {
			return Double.parseDouble(getParam(key));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public void addParam(String key, String value) {
		params.put(key, value);
	}

	@Override
	public String toString() {
		return "ip:" + ip + ",port:" + port + ",params:" + params;
	}
}
