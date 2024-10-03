package net.onedsix.ratils.trimap;

import net.onedsix.ratils.trimap.hash.HashTriMap;
import net.onedsix.ratils.trimap.hash.TreeTriMap;
import net.onedsix.ratils.trimap.hash.WeakHashTriMap;
import net.onedsix.ratils.trimap.list.ArrayTriList;
import net.onedsix.ratils.trimap.list.LinkedTriList;
import net.onedsix.ratils.trimap.list.TriArray;

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
	 * @see Map#size() */
	int dividerSize();

	/** Similar to {@link Map#size()}, but instead measures the largest internal map's size.<br>
	 * This is an expensive operation, do not call is repeatedly, especially if the map is being actively modified.
	 * @see Map#size() */
	int largestInternalSize();

	/** Returns the total size of the map, counting every entry on the table.<br>
	 * This is an expensive operation, do not perform it repeatedly, especially if the map is being modified between each call.
	 * @see Map#size() */
	int totalSize();

	boolean containsDivider(D supposedDivider);
	boolean containsKey(K supposedKey);
	boolean containsKey(D divider, K supposedKey);
	boolean containsValue(V supposedValue);
	boolean containsValue(D divider, V supposedValue);

	//
	V get(K key);

	//
	V get(D divider, K key);
	
	//
	Map<K, V> getInternalMap(D divider);
	
	//
	Map<K, V> getInternalMap();

	//
	V put(K key, V value);

	//
	V put(D divider, K key, V value);

	/** Adds the map to the top level map at that divider. */
	void putAll(D divider, Map<K, V> map);

	/** Removes the entry for the given divider key and inner key, returning the removed value. */
	boolean remove(D divider, K key);

	/** Removed the sub-map for the given divider key, returning the entire removed map. */
	Map<K, V> remove(D divider);

	/** Removes all entries from the map */
	void clear();
	
	/** Changes the preferred divider for methods without one.
	 * This must be called before {@link TriMap#put(K, V)} or {@link TriMap#get(K)}*/
	D setPreferredDivider(D divider);
	//
	D getPreferredDivider();
}
