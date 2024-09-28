package net.onedsix.ratils.trimap;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractTriMap<D, K, V> implements TriMap<D, K, V> {
	public Map<D, Map<K, V>> nodes;
	public D preferredDivider;
	public boolean modifiedSinceLastInternalsSizeCheck = true, modifiedSinceLastTotalSizeCheck = true;
	public int largestInternalSize = Integer.MAX_VALUE, totalSize = Integer.MAX_VALUE;

	protected AbstractTriMap(Map<D, Map<K, V>> nodes, D preferredDivider) {
		this.nodes = nodes;
		this.preferredDivider = preferredDivider;
	}

	@Override
	public int dividerSize() {
		return nodes.size();
	}

	@Override
	public int largestInternalSize() {
		if (modifiedSinceLastInternalsSizeCheck || largestInternalSize == Integer.MAX_VALUE) {
			AtomicInteger tempSize = new AtomicInteger();
			nodes.forEach((ignored, value) -> {
				if (value.size() > tempSize.get()) tempSize.set(value.size());
			});
			largestInternalSize = tempSize.get();
		}
		return largestInternalSize;
	}

	@Override
	public int totalSize() {
		if (modifiedSinceLastTotalSizeCheck || totalSize == Integer.MAX_VALUE) {
			AtomicInteger tempSize = new AtomicInteger();
			nodes.forEach((ignored, value) -> tempSize.addAndGet(value.size()));
			totalSize = tempSize.get();
		}
		return totalSize;
	}

	@Override
	public boolean containsDivider(D supposedDivider) {
		return nodes.containsKey(supposedDivider);
	}

	@Override
	public boolean containsKey(K supposedKey) {
		return this.containsKey(preferredDivider, supposedKey);
	}

	@Override
	public boolean containsKey(D divider, K supposedKey) {
		return nodes.get(divider).containsKey(supposedKey);
	}

	@Override
	public boolean containsValue(V supposedValue) {
		return this.containsValue(preferredDivider, supposedValue);
	}

	@Override
	public boolean containsValue(D divider, V supposedValue) {
		return nodes.get(divider).containsValue(supposedValue);
	}

	@Override
	public V get(D divider, K key) {
		return nodes.get(divider).get(key);
	}

	@Override
	public V get(K key) {
		return nodes.get(this.preferredDivider).get(key);
	}

	@Override
	public Map<K, V> getInternalMap(D divider) {
		return nodes.get(divider);
	}

	@Override
	public Map<K, V> getInternalMap() {
		return nodes.get(preferredDivider);
	}

	@Override
	public V put(D divider, K key, V value) {
		return nodes.get(divider).put(key, value);
	}

	@Override
	public V put(K key, V value) {
		return this.put(preferredDivider, key, value);
	}

	@Override
	public void putAll(D divider, Map<K, V> map) {
		nodes.put(divider, map);
	}

	@Override
	@SuppressWarnings("all") // No clue why the key part is bugging out, as long as key != null you should be ok.
	public boolean remove(D divider, @NotNull K key) {
		return nodes.remove(divider, key);
	}

	@Override
	public Map<K, V> remove(D divider) {
		return nodes.remove(divider);
	}

	@Override
	public void clear() {
		nodes.clear();
	}

	@Override
	public D setPreferredDivider(D divider) {
		preferredDivider = divider;
		return preferredDivider;
	}
	
	@Override
	public D getPreferredDivider() {
		return preferredDivider;
	}
}
