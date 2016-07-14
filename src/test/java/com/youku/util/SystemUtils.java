package com.youku.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public class SystemUtils {

	private static final String winBasePath = "C:/Users/loda/git/minRpc/src/main/resources";

	private static final String otherBasePath = "/Users/loda/git/minRpc/src/main/resources";

	public static final String getBasePath() {
		String os = StringUtils.lowerCase(System.getProperty("os.name"));
		Assert.notNull(os);
		if (os.startsWith("windows")) {
			return winBasePath;
		} else {
			return otherBasePath;
		}
	}

	public static void main(String[] args) {
		System.out.println(getBasePath());
	}

}
