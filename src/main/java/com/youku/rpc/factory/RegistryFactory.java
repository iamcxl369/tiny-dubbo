package com.youku.rpc.factory;

import com.youku.rpc.registry.Registry;
import com.youku.rpc.remote.URL;

public interface RegistryFactory {

	Registry getRegistry(URL url);
}
