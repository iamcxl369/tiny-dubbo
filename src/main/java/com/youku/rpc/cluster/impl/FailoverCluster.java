package com.youku.rpc.cluster.impl;

import com.youku.rpc.cluster.Cluster;
import com.youku.rpc.cluster.Directory;
import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.invoker.impl.FailoverClusterInvoker;

public class FailoverCluster implements Cluster{

	@Override
	public Invoker join(Directory directory) {
		return new FailoverClusterInvoker(directory);
	}


}
