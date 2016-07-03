package com.youku.rpc.remote.serialize.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.youku.rpc.remote.serialize.Serializer;

public class FastjsonSerializer implements Serializer {

	private static final Logger log = LoggerFactory.getLogger(FastjsonSerializer.class);

	@Override
	public byte[] serialize(Object obj) {
		log.info("采用fastjson序列化");
		return JSON.toJSONBytes(obj, SerializerFeature.WriteClassName);
		// return JSON.toJSONBytes(obj);
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> targetClass) {
		log.info("采用fastjson反序列化");
		return JSON.parseObject(data, targetClass);
	}

}
