package com.youku.rpc.cluster.loadbalance;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import com.youku.rpc.common.Const;
import com.youku.rpc.invoker.Invoker;

public abstract class AbstractLoadBalance implements LoadBalance {

	protected int getWeight(Invoker invoker) {
		String weightString = invoker.getURL().getParam("weight");
		return NumberUtils.toInt(weightString, Const.DEFAULT_WEIGHT);
	}

	protected List<Invoker> filterIllegalWeight(List<Invoker> invokers) {
		List<Invoker> newInvokers = new ArrayList<>();

		for (Invoker invoker : invokers) {
			int weight = getWeight(invoker);
			if (weight > 0) {
				newInvokers.add(invoker);
			}
		}

		return newInvokers;
	}

	@Override
	public Invoker select(List<Invoker> invokers) {
		return doSelect(filterIllegalWeight(invokers));
	}

	protected abstract Invoker doSelect(List<Invoker> invokers);

}
