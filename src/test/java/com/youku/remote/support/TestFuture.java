package com.youku.remote.support;

import com.youku.rpc.common.UUIDUtil;
import com.youku.rpc.exception.RpcException;
import com.youku.rpc.remote.support.DefaultFuture;
import com.youku.rpc.remote.support.Request;
import com.youku.rpc.remote.support.Response;

public class TestFuture {

	public static void main(String[] args) {
		final String id = UUIDUtil.uuid();
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Response response = new Response(id);
				response.setValue("haha");

				DefaultFuture.received(response);

			}
		}).start();

		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					Request request = new Request(id);
					DefaultFuture future = new DefaultFuture(1000, request);

					try {
						System.out.println(future.get());
					} catch (RpcException e) {
						e.printStackTrace();
					}
				}
			}).start();

		}

	}
}
