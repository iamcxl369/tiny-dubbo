package com.youku.rpc;

import java.io.File;

import com.youku.rpc.bootstrap.Nevermore;
import com.youku.util.SystemUtils;

public class TestServerWithConfig {

	private final static String provider = SystemUtils.getBasePath() + File.separator + "provider.xml";

	public static void main(String[] args) {
		Nevermore nevermore = new Nevermore(provider);
		nevermore.start();
	}
}
