package net.onedsix.ratils.trimap;

import java.util.TreeMap;

public class TreeTriMap<D, K, V> extends AbstractTriMap<D, K, V> {
	public TreeTriMap(D preferredDivider) {
		super(new TreeMap<>(), preferredDivider);
		nodes.put(preferredDivider, new TreeMap<>());
	}
}
