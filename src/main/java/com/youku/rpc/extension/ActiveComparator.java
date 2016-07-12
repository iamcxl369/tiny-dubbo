package com.youku.rpc.extension;

import java.util.Comparator;

import org.springframework.util.Assert;

import com.youku.rpc.annotation.Active;

public class ActiveComparator implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		Active a1 = o1.getClass().getAnnotation(Active.class);
		Active a2 = o2.getClass().getAnnotation(Active.class);

		Assert.notNull(a1);
		Assert.notNull(a2);

		int order1 = a1.order();
		int order2 = a2.order();

		return order2 - order1;
	}

}
