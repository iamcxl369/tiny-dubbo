package com.youku.rpc.factory;

import com.youku.rpc.cluster.Cluster;
import com.youku.rpc.cluster.impl.FailoverCluster;

public class ClusterFactory {

	public static Cluster create(String clusterName) {
		// TODO 目前只提供failover集群容错方案
		return new FailoverCluster();
	}

}
