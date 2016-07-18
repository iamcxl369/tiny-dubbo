package com.youku.rpc.sync;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestSync {

	private static ExecutorService service = Executors.newFixedThreadPool(10);

	static int i = 0;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// Future<Integer> f1 = null;
		// for (i = 0; i < 10; i++) {
		// f1 = service.submit(new Callable<Integer>() {
		//
		// @Override
		// public Integer call() throws Exception {
		// Thread.sleep(5000);
		// return i;
		// }
		// });
		// System.out.println(f1.get());
		// }
		//
		// service.shutdown();

		System.out.println("xx" + System.getProperty("line.separator") + "yy");
	}
}
