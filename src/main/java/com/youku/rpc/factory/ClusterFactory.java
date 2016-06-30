package com.youku.rpc.factory;

import com.youku.rpc.cluster.Cluster;
import com.youku.rpc.extension.ExtensionLoader;

public class ClusterFactory {

	public static Cluster create(String clusterName) {
		return ExtensionLoader.getExtension(Cluster.class, clusterName);
	}

}
