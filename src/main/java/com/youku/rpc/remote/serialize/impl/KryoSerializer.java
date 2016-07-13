package com.youku.rpc.remote.serialize.impl;

import java.lang.reflect.InvocationHandler;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import com.youku.rpc.remote.serialize.Serializer;

import de.javakaffee.kryoserializers.ArraysAsListSerializer;
import de.javakaffee.kryoserializers.BitSetSerializer;
import de.javakaffee.kryoserializers.GregorianCalendarSerializer;
import de.javakaffee.kryoserializers.JdkProxySerializer;
import de.javakaffee.kryoserializers.RegexSerializer;
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import de.javakaffee.kryoserializers.URISerializer;
import de.javakaffee.kryoserializers.UUIDSerializer;
import de.javakaffee.kryoserializers.UnmodifiableCollectionsSerializer;

public class KryoSerializer implements Serializer {

	private static final Logger log = LoggerFactory.getLogger(KryoSerializer.class);

	private Kryo kryo;

	public KryoSerializer() {
		kryo = new Kryo();
		 kryo.setReferences(false);
		 kryo.setRegistrationRequired(false);
		
		 kryo.register(Arrays.asList("").getClass(), new
		 ArraysAsListSerializer());
		 kryo.register(GregorianCalendar.class, new
		 GregorianCalendarSerializer());
		 kryo.register(InvocationHandler.class, new JdkProxySerializer());
		 kryo.register(BigDecimal.class, new
		 DefaultSerializers.BigDecimalSerializer());
		 kryo.register(BigInteger.class, new
		 DefaultSerializers.BigIntegerSerializer());
		 kryo.register(Pattern.class, new RegexSerializer());
		 kryo.register(BitSet.class, new BitSetSerializer());
		 kryo.register(URI.class, new URISerializer());
		 kryo.register(UUID.class, new UUIDSerializer());
		 UnmodifiableCollectionsSerializer.registerSerializers(kryo);
		 SynchronizedCollectionsSerializer.registerSerializers(kryo);
		
		 // now just added some very common classes
		 // optimization
		 kryo.register(HashMap.class);
		 kryo.register(ArrayList.class);
		 kryo.register(LinkedList.class);
		 kryo.register(HashSet.class);
		 kryo.register(TreeSet.class);
		 kryo.register(Hashtable.class);
		 kryo.register(Date.class);
		 kryo.register(Calendar.class);
		 kryo.register(ConcurrentHashMap.class);
		 kryo.register(SimpleDateFormat.class);
		 kryo.register(GregorianCalendar.class);
		 kryo.register(Vector.class);
		 kryo.register(BitSet.class);
		 kryo.register(StringBuffer.class);
		 kryo.register(StringBuilder.class);
		 kryo.register(Object.class);
		 kryo.register(Object[].class);
		 kryo.register(String[].class);
		 kryo.register(byte[].class);
		 kryo.register(char[].class);
		 kryo.register(int[].class);
		 kryo.register(float[].class);
		 kryo.register(double[].class);
	}

	@Override
	public byte[] serialize(Object obj) {
		log.info("采用kryo序列化");
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Output output = new Output(os);
		kryo.writeClassAndObject(output, obj);
		output.flush();
		return os.toByteArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialize(byte[] data) {
		log.info("采用kryo反序列化");
		Input input = new Input(data);
		return (T) kryo.readClassAndObject(input);
	}

}
