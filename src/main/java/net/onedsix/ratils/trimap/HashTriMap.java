package net.onedsix.ratils.trimap;

import java.util.HashMap;

public class HashTriMap<D, K, V> extends AbstractTriMap<D, K, V> {
	public HashTriMap(D preferredDivider) {
		super(new HashMap<>(), preferredDivider);
		nodes.put(preferredDivider, new HashMap<>());
	}
}
