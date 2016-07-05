package com.youku.rpc.remote.serialize.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.youku.rpc.remote.serialize.Serializer;

public class KryoSerializer implements Serializer {

	private static final Logger log = LoggerFactory.getLogger(KryoSerializer.class);

	private Kryo kryo = new Kryo();

	@Override
	public byte[] serialize(Object obj) {
		log.info("采用kryo序列化");
		Output output = new Output(4096);
		kryo.writeClassAndObject(output, obj);
		return output.getBuffer();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] data) {
		log.info("采用kryo反序列化");
		Input input = new Input(data);
		return (T) kryo.readClassAndObject(input);
	}

}
