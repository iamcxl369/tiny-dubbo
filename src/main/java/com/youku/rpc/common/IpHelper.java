package com.youku.rpc.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpHelper {

	public static String getLocalIp() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

}
