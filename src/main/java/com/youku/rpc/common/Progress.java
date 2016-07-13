package com.youku.rpc.common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Progress {

	private final Logger log = LoggerFactory.getLogger(Process.class);

	private CountDownLatch latch = new CountDownLatch(1);

	public void process(long timeout, TimeUnit unit) {
		try {
			log.info("等待任务执行");
			latch.await(timeout, unit);
			log.info("任务执行完成");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void done() {
		if (latch != null) {
			latch.countDown();
		}
	}

}
