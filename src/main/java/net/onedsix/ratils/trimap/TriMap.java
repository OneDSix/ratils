package net.onedsix.ratils.trimap;

import java.util.Map;

/**A {@link Map} of nested {@link Map}s, allowing for more complex data to be stored in a single object.<br>
 * Internally, all the {@link TriMap} variants are different, some being better at memory efficiency, some at computational efficiency.<br>
 * {@link TriMap} will never be compatible with the base Java {@link Map} as it was originally intended,
 * as their implementations are too different.<br>
 * <br>
 * This interface and inheritors are currently used in 1D6's Translation engine, and as a form of specialized player
 * data storage, prime examples of {@link TriMap}'s use cases.<br>
 * @param <D> Divider, separates the table into separate tables.
 * @param <K> Key, acts like a normal Map key
 * @param <V> Value, acts like a normal Map value
 * @see AbstractTriList AbstractTriList - Middle Ground; Mix of the two implementations
 * @see LinkedTriList
 * @see ArrayTriList
 * @see AbstractTriMap AbstractTriMap - Computationally Efficent, large object count
 * @see HashTriMap
 * @see TreeTriMap
 * @see WeakHashTriMap
 * @see TriArray TriArray - Memory Efficent, cumbersome to work with
 * */
@SuppressWarnings("unused")
public interface TriMap<D, K, V> {

	/** Returns the size of the top-level dividing map.
	 * @see Map#size()
	 * @see #largestInternalSize()
	 * @see #size() */
	int dividerSize();

	/** Measures the largest internal map's size.<br>
	 * This is an expensive operation, do not call is repeatedly, especially if the map is being actively modified.
	 * @throws UnsupportedOperationException if this map does not support this operation.
	 * @see Map#size()
	 * @see #dividerSize()
	 * @see #size() */
	int largestInternalSize() throws UnsupportedOperationException;

	/** Returns the total size of the map, counting every entry on the table.<br>
	 * This is an expensive operation, do not perform it repeatedly, especially if the map is being modified between each call.
	 * @see Map#size()
	 * @see #dividerSize()
	 * @see #largestInternalSize() */
	int size();

	/** Checks if the specified divider exists in the map */
	boolean containsDivider(D supposedDivider);
	/** Checks if the specified key exists in the preferred divider's sub-map
	 * @see Map#containsKey(Object) */
	boolean containsKey(K supposedKey);
	/** Checks if the specified divider-key pair exists in the tri-map
	 * @see Map#containsKey(Object) */
	boolean containsKey(D divider, K supposedKey);
	/** Checks if the specified value exists in the preferred divider's sub-map
	 * @see Map#containsValue(Object) */
	boolean containsValue(V supposedValue) throws UnsupportedOperationException;
	/** Checks if the specified divider-value pair exists in the tri-map
	 * @see Map#containsValue(Object) */
	boolean containsValue(D divider, V supposedValue) throws UnsupportedOperationException;

	/** Returns the value of the specified key in the preferred sub-map
	 * @see Map#get(Object)  */
	V get(K key);
	/** Returns the value of the specified divider-key pair
	 * @see Map#get(Object)  */
	V get(D divider, K key);
	
	/** Returns the sub-map of the preferred divider */
	Map<K, V> getSubmap();
	/** Returns the sub-map of the specified divider */
	Map<K, V> getSubmap(D divider);

	/** Puts a vale to the preferred sub-map
	 * @see Map#put(Object, Object) */
	V put(K key, V value);
	/** Puts a value to the specified sub-map
	 * @see Map#put(Object, Object) */
	V put(D divider, K key, V value);

	/** Adds the map to the top level map at that divider.
	 * @see Map#putAll(Map) */
	void putAll(D divider, Map<K, V> map);

	/**
	 * Removes the entry for the given divider key and inner key, returning the removed value.
	 *
	 * @throws UnsupportedOperationException if this map does not support this operation.
	 * @see #removeSubmap(K)
	 * @see Map#remove(Object)
	 */
	V removeEntry(D divider, K key) throws UnsupportedOperationException;
	/** Removed the sub-map for the given divider key, returning the entire removed map.
	 * @throws UnsupportedOperationException if this map does not support this operation.
	 * @see #removeEntry(D, K)
	 * @see Map#remove(Object) */
	Map<K, V> removeSubmap(D divider) throws UnsupportedOperationException;

	/** Removes all entries from the map
	 * @see Map#clear() */
	void clear();
	
	/** Changes the preferred divider for methods without one.
	 * This must be called before {@link TriMap#put(K, V)}, {@link TriMap#get(K)},
	 * or any other method that doesn't have a {@code divider} input.
	 * @see #getPreferredDivider() */
	D setPreferredDivider(D divider);
	/** Returns the current preferred divider.
	 * @see #setPreferredDivider(D)  */
	D getPreferredDivider();
}
