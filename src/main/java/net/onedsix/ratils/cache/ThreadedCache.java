package net.onedsix.ratils.cache;

import net.onedsix.ratils.poly.Duo;

import java.util.Iterator;
import java.util.Map;

/** An extension of {@link SimpleCache} that allows for better thread-safety. */
public class ThreadedCache<K, V> extends SimpleCache<K, V> {
    
    /** Constructs an empty {@link SimpleCache} instance with the default initial capacity (16). */
    public ThreadedCache() {
        super();
    }
    /** Constructs an empty {@link SimpleCache} instance with the specified initial capacity. */
    public ThreadedCache(int size) {
        super(size);
    }
    
    @Override
    public int invalidate() {
        int invalidated = 0;
        synchronized (cache) {
            Iterator<Map.Entry<K, Duo<Boolean, V>>> iterator = cache.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<K, Duo<Boolean, V>> entry = iterator.next();
                if (entry.getValue().key) {
                    iterator.remove();
                    invalidated++;
                }
            }
        }
        return invalidated;
    }
}
