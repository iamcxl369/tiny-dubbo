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
		log.debug("采用fastjson序列化");
		return JSON.toJSONBytes(obj, SerializerFeature.WriteClassName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] data) {
		log.debug("采用fastjson反序列化");
		return (T) JSON.parse(data);
	}

}
