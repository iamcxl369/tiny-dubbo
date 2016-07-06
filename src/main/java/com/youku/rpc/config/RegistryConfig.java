package com.youku.rpc.config;

import org.springframework.util.Assert;

import com.youku.rpc.common.Const;

public class RegistryConfig {

	private String protocol;

	private String address;

	public RegistryConfig(String urlString) {

		Assert.notNull(urlString, "注册中心url不能为空");

		int index = urlString.indexOf(Const.REGISTRY_SEPARATOR);

		Assert.isTrue(index != -1, "url不合法，格式应该为protocol://address");

		this.protocol = urlString.substring(0, index);

		this.address = urlString.substring(index + Const.REGISTRY_SEPARATOR.length());
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
