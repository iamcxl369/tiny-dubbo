package com.youku.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class IpHelperTest {

	@Test
	public void getLocalAddress() throws UnknownHostException {
		String ip = InetAddress.getLocalHost().getHostAddress();

		print(ip);
	}

	private void print(Object o) {
		System.out.println(o);
	}
}
