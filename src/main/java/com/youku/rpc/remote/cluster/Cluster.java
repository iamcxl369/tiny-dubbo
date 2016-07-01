package com.youku.rpc.remote.cluster;

import com.youku.rpc.invoker.Invoker;

public interface Cluster {

	Invoker join(Directory directory);

}
