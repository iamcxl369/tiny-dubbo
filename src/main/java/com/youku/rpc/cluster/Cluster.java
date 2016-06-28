package com.youku.rpc.cluster;

import com.youku.rpc.invoker.Invoker;

public interface Cluster {

	Invoker join(Directory directory);

}
