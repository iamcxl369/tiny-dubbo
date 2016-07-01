package com.youku.rpc.factory;

import com.youku.rpc.extension.ExtensionLoader;
import com.youku.rpc.remote.cluster.Cluster;

public class ClusterFactory {

	public static Cluster create(String clusterName) {
		return ExtensionLoader.getExtension(Cluster.class, clusterName);
	}

}
