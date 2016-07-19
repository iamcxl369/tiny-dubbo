package com.youku.rpc.remote.client;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.youku.rpc.common.Const;
import com.youku.rpc.exception.RpcException;
import com.youku.rpc.exception.TimeoutException;
import com.youku.rpc.remote.Response;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RpcClientHandler extends SimpleChannelInboundHandler<Response> {

	private volatile Response response;

	private Lock lock = new ReentrantLock();

	private Condition condition = lock.newCondition();

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {
		this.response = response;
		condition.signal();
	}

	private boolean isDone() {
		return response != null;
	}

	public Response getResponse() throws RpcException {
		return getResponse(Const.DEFAULT_TIMEOUT);
	}

	public Response getResponse(long timeout) throws RpcException {
		if (!isDone()) {
			long start = System.currentTimeMillis();
			lock.lock();
			try {
				while (!isDone()) {
					condition.await(timeout, TimeUnit.MILLISECONDS);
					if (isDone() || (System.currentTimeMillis() - start > timeout)) {
						break;
					}
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				lock.unlock();
			}
		}

		if (!isDone()) {
			throw new TimeoutException("获取response超过了指定时间" + timeout + "毫秒");
		} else {
			return response;
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
