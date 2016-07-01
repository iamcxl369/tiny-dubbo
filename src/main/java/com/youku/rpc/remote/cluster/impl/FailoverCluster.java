package com.youku.rpc.remote.cluster.impl;

import com.youku.rpc.invoker.Invoker;
import com.youku.rpc.invoker.impl.FailoverClusterInvoker;
import com.youku.rpc.remote.cluster.Cluster;
import com.youku.rpc.remote.cluster.Directory;

public class FailoverCluster implements Cluster{

	@Override
	public Invoker join(Directory directory) {
		return new FailoverClusterInvoker(directory);
	}


}
