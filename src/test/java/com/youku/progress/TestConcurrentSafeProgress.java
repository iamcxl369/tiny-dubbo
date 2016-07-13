package com.youku.progress;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestConcurrentSafeProgress {

	public static void main(String[] args) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				NoSafeProgress.getInstance().process(10000, TimeUnit.MILLISECONDS);
				System.out.println("hi");
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				NoSafeProgress.getInstance().process(10000, TimeUnit.MILLISECONDS);
				System.out.println("hello");
			}
		}).start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				NoSafeProgress.getInstance().done();

			}
		}).start();

	}

	static public class Progress {

		private final Logger log = LoggerFactory.getLogger(Process.class);

		private ThreadLocal<CountDownLatch> threadLocalLatch = new ThreadLocal<>();

		private Progress() {
		}

		private static class ProgressHolder {
			public static final Progress process = new Progress();
		}

		public static Progress getInstance() {
			return ProgressHolder.process;
		}

		public void process(long timeout, TimeUnit unit) {
			setCountDownLatch(new CountDownLatch(1));
			try {
				log.info("等待任务执行");
				getCountDownLatch().await(timeout, unit);
				log.info("任务执行完成");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void setCountDownLatch(CountDownLatch countDownLatch) {
			threadLocalLatch.set(countDownLatch);
		}

		private CountDownLatch getCountDownLatch() {
			return threadLocalLatch.get();
		}

		public void done() {
			CountDownLatch latch = getCountDownLatch();
			if (latch != null) {
				latch.countDown();
			}
		}

	}

	static public class NoSafeProgress {

		private final Logger log = LoggerFactory.getLogger(Process.class);

		private CountDownLatch latch;

		private NoSafeProgress() {
		}

		private static class ProgressHolder {
			public static final NoSafeProgress process = new NoSafeProgress();
		}

		public static NoSafeProgress getInstance() {
			return ProgressHolder.process;
		}

		public void process(long timeout, TimeUnit unit) {
			latch = new CountDownLatch(1);
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
}
