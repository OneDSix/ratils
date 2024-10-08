package net.onedsix.ratils.cache;

import net.onedsix.ratils.poly.Duo;

import java.lang.ref.WeakReference;
import java.util.*;

/** A slightly more memory efficient {@link SimpleCache}. */
public class WeakCache<K, V> implements Cache<K, V> {
    public final int maxSize;
    public final Map<K, WeakReference<Duo<Boolean, V>>> cache;
    
    //
    public WeakCache() {
        this(16);
    }
    //
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
        // this is a mess
        WeakReference<Duo<Boolean, V>> ref = cache.get(key);
        Duo<Boolean, V> val = ref.get();
        V returns = null;
        if (val != null) returns = val.value;
        ref.enqueue();
        return returns;
    }
    
    @Override
    public V put(K key, V val) {
        WeakReference<Duo<Boolean, V>> ref = cache.put(key, new WeakReference<>(new Duo<>(false, val)));
        Duo<Boolean, V> duoRef = ref == null ? null : ref.get();
        return duoRef == null ? null : duoRef.value;
    }
    
    @Override
    public V get(K key) {
        WeakReference<Duo<Boolean, V>> ref = cache.get(key);
        Duo<Boolean, V> duoRef = ref == null ? null : ref.get();
        return duoRef == null ? null : duoRef.value;
    }
    
    @Override
    public V remove(K key) {
        WeakReference<Duo<Boolean, V>> ref = cache.remove(key);
        Duo<Boolean, V> duoRef = ref == null ? null : ref.get();
        return duoRef == null ? null : duoRef.value;
    }
    
    @Override
    public int invalidate() {
        int invalidated = 0;
        Iterator<Map.Entry<K, WeakReference<Duo<Boolean, V>>>> iterator = cache.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<K, WeakReference<Duo<Boolean, V>>> entry = iterator.next();
            Duo<Boolean, V> duo = entry.getValue().get();
            if (duo != null && duo.key) {
                iterator.remove();
                entry.getValue().enqueue(); // for good measure
                invalidated++;
            }
        }
        return invalidated;
    }
}
