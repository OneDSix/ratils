package net.onedsix.ratils.cache;

import java.util.Map;

@SuppressWarnings("unused")
public interface Cache<K,V> extends AutoCloseable {
    /** See {@link Map#size()} */
    int size();
    
    /** Marks a key to be removed next time {@link Cache#invalidate()} is called.
     * Does nothing if the key is already set to invalidate.
     * @see Cache#recache(K) */
    default V uncache(K key) {
        return this.setCache(key, true);
    }
    
    /** Unmarks a key to <b>not</b> be removed next time {@link Cache#invalidate()} is called.
     * The opposite of {@link Cache#uncache(K)}.
     * This probably shouldn't be used, as its possible the key can be removed before this method is called.
     * @see Cache#uncache(K) */
    default void recache(K key) {
        this.setCache(key, false);
    }
    
    /** Internal method, do not use. */
    V setCache(K key, boolean set);
    
    /** See {@link java.util.Map#put(Object, Object)}. */
    V put(K key, V val);
    
    /** See {@link java.util.Map#get(Object)}. */
    V get(K key);
    
    /** See {@link java.util.Map#remove(Object)}. */
    V remove(K key);
    
    /** Reads the entire cache and removes any invalidated values.<br>
     * Use {@link Cache#uncache(K)} to remove values.<br>
     * <br>
     * This is called whenever any of the below are called:<br>
     * <ul>
     *     <li>{@link Cache#put(K, V)}</li>
     *     <li>{@link Cache#remove(K)}</li>
     *     <li>{@link AutoCloseable#close()}</li>
     * </ul>
     * Its recommended to call this <b>before</b> calling {@link System#gc()} or equivalent.
     * */
    int invalidate();
    
    
    @Override default void close() {
        invalidate();
    }
}
