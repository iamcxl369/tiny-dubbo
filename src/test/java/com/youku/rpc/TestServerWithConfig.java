package com.youku.rpc;

import com.youku.rpc.bootstrap.Nevermore;

public class TestServerWithConfig {

	public final static String provider = "C:/Users/loda/git/minRpc/src/main/resources/provider.xml";

	public static void main(String[] args) {
		Nevermore nevermore = new Nevermore(provider);
		nevermore.start();
	}
}
