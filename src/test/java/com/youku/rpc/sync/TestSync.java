package com.youku.rpc.sync;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestSync {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<Integer> ft = new FutureTask<>(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				Thread.sleep(3000);
				return 1;
			}
		});

		ft.run();

//		System.out.println("begin calculate");
//
//		System.out.println(ft.get());
//
//		System.out.println("end calculate");
	}
}
