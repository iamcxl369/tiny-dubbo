package com.youku.rpc.remote.cluster.loadbalance.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import org.springframework.util.Assert;

import com.youku.rpc.common.Const;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.remote.cluster.loadbalance.LoadBalance;

public abstract class AbstractLoadBalance implements LoadBalance {

	protected int getWeight(Invoker invoker) {
		String weightString = invoker.getURL().getParam(Const.WEIGHT);
		return NumberUtils.toInt(weightString, Const.DEFAULT_WEIGHT);
	}

	protected List<Invoker> filterIllegalWeight(List<Invoker> invokers) {
		Assert.notEmpty(invokers, "不存在远程主机");

		List<Invoker> newInvokers = new ArrayList<>();

		for (Invoker invoker : invokers) {
			int weight = getWeight(invoker);
			if (weight > 0) {
				newInvokers.add(invoker);
			}
		}

		Assert.notEmpty(newInvokers, "不存在权重为正数的远程主机");

		return newInvokers;
	}

	@Override
	public Invoker select(List<Invoker> invokers) {
		return doSelect(filterIllegalWeight(invokers));
	}

	protected abstract Invoker doSelect(List<Invoker> invokers);

}
