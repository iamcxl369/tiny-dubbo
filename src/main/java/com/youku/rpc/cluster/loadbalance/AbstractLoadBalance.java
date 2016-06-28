package com.youku.rpc.cluster.loadbalance;

import org.apache.commons.lang3.math.NumberUtils;

import com.youku.rpc.common.Const;
import com.youku.rpc.invoker.Invoker;

public abstract class AbstractLoadBalance implements LoadBalance {

	protected int getWeight(Invoker invoker) {
		String weightString = invoker.getURL().getParam("weight");
		return NumberUtils.toInt(weightString, Const.DEFAULT_WEIGHT);
	}
}
