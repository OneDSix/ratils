package net.onedsix.ratils.cache;

import net.onedsix.ratils.poly.Duo;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakCache<K, V> implements Cache<K, V> {
    public final int maxSize;
    public final Map<K, WeakReference<V>> cache;
    
    public WeakCache() {
        this(16);
    }
    
    public WeakCache(int size) {
        this.maxSize = size;
        this.cache = Collections.synchronizedMap(new WeakHashMap<>(size));
    }
    
    @Override
    public int size() {
        return cache.size();
    }
    
    @Override
    public V setCache(K key, boolean set) {
        WeakReference<V> ref = cache.get(key);
        V val = ref.get();
        ref.enqueue();
        return val;
    }
    
    @Override
    public V put(K key, V val) {
        WeakReference<V> ref = cache.put(key, new WeakReference<>(val));
        return ref == null ? null : ref.get();
    }
    
    @Override
    public V get(K key) {
        WeakReference<V> ref = cache.get(key);
        return ref == null ? null : ref.get();
    }
    
    @Override
    public V remove(K key) {
        WeakReference<V> ref = cache.remove(key);
        return ref == null ? null : ref.get();
    }
    
    @Override
    public int invalidate() {
        return 0;
    }
}
