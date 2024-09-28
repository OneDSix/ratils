package net.onedsix.ratils.trimap;

import java.util.WeakHashMap;

public class WeakHashTriMap<D, K, V> extends AbstractTriMap<D, K, V> {
	public WeakHashTriMap(D preferredDivider) {
		super(new WeakHashMap<>(), preferredDivider);
		nodes.put(preferredDivider, new WeakHashMap<>());
	}
}
