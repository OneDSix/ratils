package net.onedsix.ratils.cache;

import net.onedsix.ratils.poly.Duo;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simple caching system based on {@link Map} and {@link Duo} that allows for temporary storage of objects.<br>
 * <br>
 * The internal {@code Duo} instance
 * */
public class SimpleCache<K, V> implements Cache<K, V> {
    
    //
    public final int maxSize;
    //
    public final Map<K, Duo<Boolean, V>> cache;
    
    /** Constructs an empty {@link SimpleCache} instance with the default initial capacity (16). */
    public SimpleCache() {
        this(16);
    }
    /** Constructs an empty {@link SimpleCache} instance with the specified initial capacity. */
    public SimpleCache(int size) {
        this.maxSize = size;
        this.cache = Collections.synchronizedMap(new LinkedHashMap<K, Duo<Boolean, V>>(size, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, Duo<Boolean, V>> eldest) {
                return size() > SimpleCache.this.maxSize && eldest.getValue().key;
            }
        });
    }
    
    @Override
    public int size() {
        return cache.size();
    }
    
    @Override
    public V setCache(K key, boolean set) throws NullPointerException {
        Duo<Boolean, V> duo = cache.get(key);
        if (duo == null) throw new NullPointerException("This key does not exist!");
        duo.key = set;
        cache.put(key, duo);
        return duo.value;
    }
    
    @Override
    public V put(K key, V val) {
        invalidate();
        Duo<Boolean, V> duo = cache.put(key, new Duo<>(false, val));
        return duo == null ? null : duo.value;
    }
    
    @Override
    public V get(K key) {
        Duo<Boolean, V> duo = cache.get(key);
        return duo == null ? null : duo.value;
    }
    
    @Override
    public V remove(K key) {
        invalidate();
        Duo<Boolean, V> duo = cache.remove(key);
        return duo == null ? null : duo.value;
    }
    
    @Override
    public int invalidate() {
        int invalidated = 0;
        Iterator<Map.Entry<K, Duo<Boolean, V>>> iterator = cache.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, Duo<Boolean, V>> entry = iterator.next();
            if (entry.getValue().key) {
                iterator.remove();
                invalidated++;
            }
        }
        return invalidated;
    }
}
